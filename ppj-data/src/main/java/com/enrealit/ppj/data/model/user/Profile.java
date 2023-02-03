package com.enrealit.ppj.data.model.user;

import java.time.LocalDate;
import com.enrealit.ppj.shared.enums.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

	private ProfileType type;
	private LocalDate startDate;
	private LocalDate endDate;

}
