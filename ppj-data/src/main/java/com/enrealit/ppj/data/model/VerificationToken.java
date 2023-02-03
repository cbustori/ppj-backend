package com.enrealit.ppj.data.model;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import com.enrealit.ppj.shared.enums.VerificationTokenType;
import lombok.Data;

/**
 * 
 * @author vincent.mercier
 *
 */
@Data
@Document(collection = "verification_token")
public class VerificationToken {
  private String id;
  private String token;
  private String userId;
  private VerificationTokenType type;
  private Date expiryDate;

}
