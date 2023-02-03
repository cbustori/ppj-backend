package com.enrealit.ppj.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.enrealit.ppj.data.model.VerificationToken;
import com.enrealit.ppj.shared.enums.VerificationTokenType;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {

	VerificationToken findByToken(String token);
	
	void deleteByUserIdAndType(String userId, VerificationTokenType type);

}
