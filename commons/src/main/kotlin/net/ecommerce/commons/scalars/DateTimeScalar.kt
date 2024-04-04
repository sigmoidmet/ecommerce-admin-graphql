package net.ecommerce.commons.scalars

import com.netflix.graphql.dgs.DgsScalar
import graphql.GraphQLContext
import graphql.execution.CoercedVariables
import graphql.language.StringValue
import graphql.language.Value
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@DgsScalar(name = "DateTime")
class DateTimeScalar : Coercing<LocalDateTime, String> {

    override fun serialize(dataFetcherResult: Any, graphQLContext: GraphQLContext, locale: Locale): String? {
        if (dataFetcherResult is LocalDateTime) {
            return dataFetcherResult.format(DateTimeFormatter.ISO_DATE_TIME)
        } else {
            throw CoercingSerializeException("Not a valid DateTime $dataFetcherResult")
        }
    }

    override fun parseLiteral(input: Value<*>,
                              variables: CoercedVariables,
                              graphQLContext: GraphQLContext,
                              locale: Locale): LocalDateTime? {
        if (input is StringValue) {
            return parseValue(input.value, graphQLContext, locale)
        }

        throw CoercingParseLiteralException("Value is not a valid ISO date time $input")
    }

    override fun parseValue(input: Any, graphQLContext: GraphQLContext, locale: Locale): LocalDateTime? {
        return LocalDateTime.parse(input.toString(), DateTimeFormatter.ISO_DATE_TIME)
    }
}