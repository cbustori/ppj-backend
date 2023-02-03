package com.enrealit.ppj.apiserver.input;

import org.springframework.stereotype.Component;

import com.enrealit.ppj.shared.dto.CloudinaryPictureDto;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeVisitor;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudinaryPictureInput implements GraphQLInputType {

	private String publicId;
	private String url;

	public CloudinaryPictureDto convertToDto() {
		return CloudinaryPictureDto.builder().publicId(publicId).url(url).build();
	}


	@Override
	public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
