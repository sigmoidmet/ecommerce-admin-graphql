const { ApolloServer, gql } = require('apollo-server');
const {ApolloGateway, IntrospectAndCompose} = require('@apollo/gateway')

const gateway = new ApolloGateway({
    supergraphSdl: new IntrospectAndCompose({
        subgraphs: [

        ]
    })
});

const server = new ApolloServer({ gateway, subscriptions:false, tracing:true });
server.listen();