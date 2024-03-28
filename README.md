## Requirements:
Create several microservices:
1. schema-service - service which is responsible for CRUD for storing and validating federated schema
2. Gateway - routes all the requests via GraphQL Federation
3. user-events-service - manipulates all the information about user events
   1. Abandonment - an object which stores information about browse, cart or checkout that was abandoned by a customer, e.g. if more than 10 minutes left after browse without consecutive add-to-cart, after add-to-cart without consecutive checkout or checkout without consecutive purchase-complete
      1. Fields:
         1. id
         2. checkoutPayload
            1. url - for buyer to recover their checkout
            2. productsQuantity
            3. totalPrice - [Monetary], the sum of all products prices, including discounts
            4. appId - app id associated with an abandoned checkout, the app itself is stored in the another microservice
         3. type - enum: BROWSE, CART, CHECKOUT
         4. createdAt - date and time when the abandonment was created
         5. customerId - customer id who abandoned this event, customer itself is stored in the another microservice
         6. cartUrl - url of the abandoned cart
         7. visitStartedAt - when the customer who abandoned this event has started the visit which lead to this
      2. Connections:
         1. productsAddedToCart - added to cart products during the abandoned visit, ids, [Product] itself is defined in inventory-service
         2. productsViewed - viewed products during the abandoned visit, ids, [Product] itself is defined in inventory-service
      3. Queries:
         1. abandonment(id) - returns an abandonment by id
4. apps-service - manipulates all the information about apps which a shop can connect
   1. App 
      1. Fields:
         1. id
         2. apiKey - a unique application API identifier
         3. appStoreAppUrl - app store page URL of the app
         4. appStoreDeveloperUrl - app store developer URL of the app
         5. availablePermissions - [Permission], what permissions an app has to your store
         6. image - [Image] for the app
         7. description
         8. developerName
         9. features - a list of strings
         10. installUrl - a webpage where you can install the app
         11. pricingDetails
         12. privacyPolicyUrl
         13. requestedPermissions - [Permission]
         14. title
      2. Queries:
         1. app(id) - find an app by id
         2. appByKey(apiKey) - find an app by apiKey
   2. Permission 
      1. Fields:
         1. action - enum: READ or WRITE
         2. resource - string, products, orders, etc.
   3. AppDiscount - what discount app provides to customers which accessed your products through it, app should have READ discounts to access it 
      1. Fields:
         1. id
         2. app - [App], which provides this discount type
         3. discountId - id of a discount, the discount itself is stored in the another microservice
      2. Queries:
         1. appDiscount(id)
         2. appDiscounts() - all the discounts provided by installed apps
5. delivery-service - service which is managing delivery information
   1. DeliveryProvider
      1. Fields:
         1. id
         2. availableForCountries - a list
            1. countryCode
            2. price - [Monetary]
         3. name - name of the delivery provider
         4. icon - [Image]
      2. Queries:
         1. deliveryProvider(id)
         2. deliveryProvidersByCountryCode(countryCode) - list of available delivery providers for a country 
6. inventory-service 
   1. Catalog - interface which represents a list of products with publishing and pricing information
      1. Fields:
         1. id
         2. priceList - [PriceList]
         3. products - list of [Product]
         4. status - enum: ACTIVE, ARCHIVED, DRAFT
         5. title
   2. AppCatalog - implementation of [Catalog] interface which defines a catalog published by an app
      1. Connections:
         1. apps - an array of app ids which are associated with this catalog, [App] itself is defined in app-service
   3. MarketCatalog - implementation of [Catalog] interface which defines a catalog published for a specific markets
      1. Connections:
         1. markets - an array of [Market] objects
   4. PriceList - information about prices and eligibility rules
      1. Fields:
         1. id
         2. catalog
         3. currencyCode
         4. name - should be unique
      2. Connections:
         1. prices - of [Price] type
      3. Queries
         1. priceList(id)
         2. priceLists() - all for a shop
      4. Mutations:
         1. priceListCreate(priceListCreateInput):
            1. catalogId
            2. currencyCode
            3. name
         2. priceListPricesUpdate
            1. priceListId
            2. pricesToAdd:
               1. price - [Monetary]
               2. productId
            3. pricesToDeleteByProductId - ids of products
   5. Price
      1. Fields:
         1. price - [Monetary]
         2. variant - [ProductVariant]
   6. Product
      1. Fields:
         1. id
         2. createdAt - ISO-8601 date
         3. description
         4. productType - string
         5. status - enum: ACTIVE, DRAFTED, ARCHIVED
         6. vendor - string
      2. Connections:
         1. images - list of [Image]
         2. variants - list of [ProductVariant]
      3. Queries:
         1. product(id)
         2. products(catalogId)
      4. Mutations:
         1. productChangeStatus(id, status)
         2. productCreate - with all the fields and connections
         3. productUpdate - with all the fields and connections, if you include some variants, but not all - other should be deleted
         4. productVariantCreate
         5. productVariantUpdate
         6. productVariantDelete(id)
         7. productVariantsBulkCreate - without deletion of other variants
         8. productVariantsBulkUpdate
         9. productVariantsBulkDelete(ids)
   7. ProductVariant
      1. Fields:
         1. id
         2. displayName
         3. inventoryQuantity
         4. product - [Product]
         5. title
      2. Queries:
         1. productVariant(variantId)
         2. productVariants(productId)
   8. Market - A market is a group of one or more regions that you want to target for international sales
      1. Fields:
         1. enabled - boolean
         2. id
         3. name
         4. url
         5. isPrimary - boolean
      2. Connections:
         1. catalogs - list of [MarketCatalog]
         2. regions - list of [Region]
      3. Queries:
         1. market(id)
         2. marketByGeography(countryCode)
         3. primaryMarket
         4. markets
      4. Mutations:
         1. marketCreate
         2. marketRegionDelete(regionId)
         3. marketRegionsCreate
         4. marketUpdate
   9. Region
      1. Fields:
         1. id
         2. name
         3. currencyCode
         4. countryCode

Common types:
1. Image:
   1. id
   2. height
   3. width
   4. url
2. Monetary:
   1. amount
   2. currencyCode - CurrencyCode - enum