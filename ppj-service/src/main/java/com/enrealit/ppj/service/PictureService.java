package com.enrealit.ppj.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.enrealit.ppj.service.exception.CloudinaryPictureUploadException;
import com.enrealit.ppj.shared.dto.CloudinaryPictureDto;
import com.enrealit.ppj.shared.dto.FileUploadDto;
import com.enrealit.ppj.shared.dto.user.UserDto;

@Service
public class PictureService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PictureService.class);

	@Autowired
	private Cloudinary cloudinary;

	public List<CloudinaryPictureDto> uploadPictures(String folder, List<FileUploadDto> files)
			throws CloudinaryPictureUploadException {
		List<CloudinaryPictureDto> pics = new ArrayList<>();
		for (FileUploadDto f : files) {
			try {
				LOGGER.info("Upload de l'image vers Cloudinary...");
				Map uploadResult = cloudinary.uploader().upload(f.getContent(), ObjectUtils.asMap("folder", folder,
						"transformation", new Transformation().crop("limit").width(500).height(313)));
				LOGGER.info("Upload terminé avec succès - URL générée: {}", uploadResult.get("url"));
				pics.add(new CloudinaryPictureDto((String) uploadResult.get("url"),
						(String) uploadResult.get("public_id")));
			} catch (Exception e) {
				throw new CloudinaryPictureUploadException("Impossible d'uploader l'image", e);
			}
		}

		return pics;
	}

	public CloudinaryPictureDto uploadProfilePicture(UserDto user, FileUploadDto file)
			throws CloudinaryPictureUploadException {
		CloudinaryPictureDto pic = null;
		try {
			LOGGER.info("Upload de l'image vers Cloudinary...");
			Map uploadResult = cloudinary.uploader()
					.upload(file.getContent(), ObjectUtils.asMap("folder", "users/avatars/" + user.getId(), "format",
							"png", "transformation",
							new Transformation().width(100).height(100).gravity("face").radius("max").crop("fill")));
			pic = new CloudinaryPictureDto((String) uploadResult.get("url"), (String) uploadResult.get("public_id"));
			LOGGER.info("Upload terminé avec succès. {}", pic);
			if (user.getProfilePicture() != null && StringUtils.isNotBlank(user.getProfilePicture().getPublicId())) {
				LOGGER.info("Suppression de l'ancien avatar: {}", user.getProfilePicture().getPublicId());
				Boolean deleted = deletePicture(user.getProfilePicture().getPublicId());
				LOGGER.info("Suppression effectuée: {}", deleted);
			}
		} catch (Exception e) {
			throw new CloudinaryPictureUploadException("Impossible d'uploader l'image", e);
		}
		return pic;
	}

	public Boolean deletePicture(String publicId) {
		try {
			Map map = cloudinary.uploader().destroy(publicId, null);
			return StringUtils.equals("ok", (String) map.get("result"));
		} catch (IOException e) {
			LOGGER.error("Impossible de supprimer l'image du repository Cloudinary", e);
			return false;
		}
	}

}
