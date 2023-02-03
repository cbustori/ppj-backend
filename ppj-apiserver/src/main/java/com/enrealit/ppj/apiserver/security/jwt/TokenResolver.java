package com.enrealit.ppj.apiserver.security.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.annotation.PostConstruct;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenResolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenResolver.class);

  @Autowired
  private ResourceLoader resourceLoader;

  @Value("${com.enrealit.ppj.security.publickey}")
  private String pathPublicKey;

  private PublicKey publicKey;

  @PostConstruct
  private void init() {
    if (StringUtils.isBlank(pathPublicKey)) {
      LOGGER.error("Path of file with public key is empty");
    } else {
      try {
        publicKey = getPublicKey();
        LOGGER.debug("TokenResolver is Configured");
      } catch (Exception e) {
        LOGGER.error("Cannot parse with public key", e);
      }
    }
  }

  public Jws<Claims> getValidateToken(String jwt) {
    if (StringUtils.isEmpty(jwt)) {
      return null;
    }
    try {
      return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwt);
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      LOGGER.info("Invalid JWT signature.");
      LOGGER.trace("Invalid JWT signature trace: {}", e);
    } catch (ExpiredJwtException e) {
      LOGGER.info("Expired JWT token.");
      LOGGER.trace("Expired JWT token trace: {}", e);
    } catch (UnsupportedJwtException e) {
      LOGGER.info("Unsupported JWT token.");
      LOGGER.trace("Unsupported JWT token trace: {}", e);
    } catch (IllegalArgumentException e) {
      LOGGER.info("JWT token compact of handler are invalid.");
      LOGGER.trace("JWT token compact of handler are invalid trace: {}", e);
    }
    return null;
  }

  private PublicKey getPublicKey()
      throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
    KeyFactory kf = KeyFactory.getInstance("RSA");

    String publicKeyContent = IOUtils.toString(
        resourceLoader.getResource(pathPublicKey).getInputStream(), StandardCharsets.UTF_8);

    publicKeyContent = publicKeyContent.replaceAll("\\n", "")
        .replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

    return kf
        .generatePublic(new X509EncodedKeySpec(Base64.getMimeDecoder().decode(publicKeyContent)));
  }

}
