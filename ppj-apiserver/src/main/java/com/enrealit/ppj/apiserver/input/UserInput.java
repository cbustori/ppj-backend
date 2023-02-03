package com.enrealit.ppj.apiserver.input;

import org.springframework.stereotype.Component;

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
public class UserInput implements GraphQLInputType {

	private String lastName;
	private String firstName;
	private String email;
	private String password;
	private String phoneNumber;

	@Override
	public TraversalControl accept(TraverserContext<GraphQLSchemaElement> context, GraphQLTypeVisitor visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
