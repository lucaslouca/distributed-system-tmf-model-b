## Table of Contents
- [1. Introduction](#introduction)
- [2. Common](#common)
	- [POST Authenticate with user credentials](#post-authenticate-with-user-credentials)
- [3. APP](#app)
- [4. IDAT](#idat)
	- [POST Fetch EID for user](#post-fetch-eid-for-user)
	- [GET Fetch clients](#get-fetch-clients)
	- [GET Fetch client profiles](#get-fetch-client-profiles)
	- [POST Create a new client profile](#post-create-a-new-client-profile)
	- [POST Update a client profile](#post-update-a-client-profile)
	- [POST Fetch a client profile by pseudonym](#post-fetch-a-client-profile-by-pseudonym)
- [5. PSNS](#psns)
	- [POST Resolve pseudonym](#post-resolve-pseudonym)
	- [POST Create counterpart pseudonym](#post-create-counterpart-pseudonym)
	- [POST Create pseudonym tuple](#post-create-pseudonym-tuple)
	- [POST Create pseudonym](#post-create-pseudonym)
- [6. VDAT](#vdat)
	- [POST Fetch case by pseudonym](#post-fetch-case-by-pseudonym)
	- [POST Create a new case](#post-create-a-new-case)
	- [POST Update a case](#post-update-a-case)
	- [POST Fetch user assigned cases](#post-fetch-user-assigned-cases)
	- [GET Options for Type](#get-options-for-type)

### Introduction
REST methods available by module.

### Common
#### POST Authenticate with user credentials
Authenticate using username and password.

> **Resource:** `/services/authenticate`

> **Method:** `POST` 

> **Parameter:** `TXLUserCredentialsDTO` the username and password

> **Returns:** `TXLUserDTO` The authenticated user.


Example:
```JavaScript
var credentials = {"username": "puser1", "password": "s3cret"};

$http.post(host + "/services/authenticate", credentials).success(function (user, status, headers) {
    var token = headers()[TXL_CONFIG.AUTH_HEADER];
    if (token) {
        console.log(token);
    }
    console.log(user);
}).error(function () {
    console.log("Authentication failed for username '"+credentials.username+"'");
});
```
### APP

### IDAT
#### POST Fetch EID for user
Get EID(PSN1) for user.

> **Resource:** `/rest/client/eidForUser`

> **Method:** `POST` 

> **Parameter:** `TXLUserDTO` the user you want its EID

> **Returns:** `TXLPsnDTO` The EID of the user.


Example:
```JavaScript
var user = {"username": "ecuser1"};
$http({
    url: TXL_CONFIG.IDAT_HOST + "/rest/client/eidForUser",
    method: "POST",
    headers: headers,
    data: user
}).success(function (eid) {
   console.log(eid);
});
```

#### GET Fetch clients
Return an array of all clients in db.

> **Resource:** `/rest/client`

> **Method:** `GET` 

> **Parameter:**

> **Returns:** `TXLClientDTO[]` Array of `TXLClientDTO`.


Example:
```JavaScript
TXLIdatServices.factory('TXLClient', function ($resource, TXLAuthenticationService, TXL_CONFIG) {
    var headers = {};
    headers[TXL_CONFIG.AUTH_HEADER] = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.IDAT_HOST);
    return $resource(TXL_CONFIG.IDAT_HOST + '/rest/client/:id', {}, {headers:headers});
});
var clients = TXLClient.query(function () {
    for (var i = 0; i < clients.length; i++) { 
        console.log(clients[i]);
    }
});
```

#### GET Fetch client profiles
Return an array of all client profiles in the db.

> **Resource:** `/rest/clientProfile`

> **Method:** `GET` 

> **Parameter:**

> **Returns:** `TXLClientProfileDTO[]` Array of `TXLClientProfileDTO`.


Example:
```JavaScript
TXLIdatServices.factory('TXLClientProfile', function ($resource, TXLAuthenticationService, TXL_CONFIG) {
    var headers = {};
    headers[TXL_CONFIG.AUTH_HEADER] = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.IDAT_HOST);
    return $resource(TXL_CONFIG.IDAT_HOST + '/rest/clientProfile/:id', {}, {headers: headers});
});
var clientProfiles = TXLClientProfile.query(function () {
    for (var i = 0; i < clientProfiles.length; i++) { 
        console.log(clientProfiles[i]);
    }
});
```

#### POST Create a new client profile
Create a new client profile with the given pseudonym.

> **Resource:** `/rest/clientProfile/add`

> **Method:** `POST` 

> **Parameter:** `TXLPsnDTO` the pseudonym for the client profile.

> **Returns:** `TXLClientProfileDTO` the client profile object


Example:
```JavaScript
var clientProfilePsn = {"type":{"name":"PSN2"},"value":"ebcf1234-abcd-1234-9c00-a45ced166c1f"};
$http({
    url: TXL_CONFIG.IDAT_HOST + "/rest/clientProfile/add",
    method: "POST",
    headers: headers,
    data: clientProfilePsn
}).success(function (clientProfile) {
    console.log(clientProfile);
});
```

#### POST Update a client profile
Update an existing client profile.

> **Resource:** `/rest/clientProfile/update`

> **Method:** `POST` 

> **Parameter:** `TXLClientProfileDTO` the client profile to update, which must include an ID.

> **Returns:** `TXLClientProfileDTO` the updated client profile object

Example:
```JavaScript
$http({
url: TXL_CONFIG.IDAT_HOST + "/rest/clientProfile/update",
    method: "POST",
    headers: headers,
    data: clientProfile
}).success(function (updatedClientProfile) {
    console.log(updatedClientProfile);
});
```

#### POST Fetch a client profile by pseudonym
Fetch a client profile that has the given pseudonym.

> **Resource:** `/rest/clientProfile/clientProfileWithPsn`

> **Method:** `POST` 

> **Parameter:** `TXLPsnDTO` the pseudonym of the client profile

> **Returns:** `TXLClientProfileDTO` the client profile object


Example:
```JavaScript
var clientProfilePsn = {"type":{"name":"PSN1"},"value":"ebad4277-399c-48a1-9c93-a38ced158c1f"};
$http({
    url: TXL_CONFIG.IDAT_HOST + "/rest/clientProfile/clientProfileWithPsn",
    method: 'POST',
    headers: headers,
    data: clientProfilePsn
}).success(function (clientProfile) {
    console.log(clientProfile);
});
```


### PSNS
#### POST Resolve pseudonym
Resolve a given pseudonym. From `PSN1 -> PSN2` or `PSN2 -> PSN1`, depending on where we come from. 
For instance a `TXLCase` holds a client profile pseudonym of type `PSN1` that needs to be resolved to a 
pseudonym of type `PSN2`, which is the pseudonym of the client profile in `IDAT`. Here we come from `VDAT` and want to
fetch something in `IDAT` (`PSN1 -> PSN2`).
 
Another example would be fetching the cases of a client with `EID (PSN1)` in `IDAT`. The `EID (PSN1)` needs to be 
resolved to `PSN2(s)`, which are the pseudonyms of `TXLCase`s in `VDAT`. Here we come from `IDAT` and want to fetch 
something in `VDAT`. Again this is a `PSN1 -> PSN2` resolving.
 
Another example is the case when we have an existing client profile (`IDAT`) that has a pseudonym (`PSN2`), that
we want to assign to a new case. Here we need to know the `PSN1` counterpart so that we can assign it to the 
`case.clientProfilePsnToBeResolved` property of the `TXLCase` in `VDAT`. For that we ask the `PSNS` to resolve a pseudonym
of type `PSN2` and give us the `PSN1` counterpart. The `PSNS` will return an array of counterparts, but it will optimally
contain only one pseudonym of type `PSN1`. This is a `PSN2 -> PSN1` resolving.

> **Resource:** `/rest/resolve`

> **Method:** `POST` 

> **Parameter:** `TXLPsnDTO ` the pseudonym to resolve

> **Returns:** `TXLPsnDTO[]` Array of counterpart pseudonyms corresponding to the pseudonym to resolve.


Example:
```JavaScript
var psnToResolve = {"type":{"name":"PSN2"},"value":"ebad4277-399c-48a1-9c93-a38ced158c1f"};
$http({
    url: TXL_CONFIG.PSNS_HOST + "/rest/resolve",
    method: "POST",
    headers: headers,
    data: psnToResolve
}).success(function (resolvedPsn) {
    for (var i = 0; i < resolvedPsn.length; i++) { 
        console.log(resolvedPsn[i]);
    }
});
```

#### POST Create counterpart pseudonym
Create a counterpart pseudonym for a given pseudonym. If the given pseudonym is of type `PSN1` then the counterpart will be of type `PSN2`. If the given pseudonym is of type `PSN2` then the counterpart will be of type `PSN1`. When called a new tuple `(PSN1, PSN2)` is persisted to the database.

> **Resource:** `/rest/addCounterpart`

> **Method:** `POST` 

> **Parameter:** `TXLPsnDTO` the pseudonym for which we want a counterpart

> **Returns:** `TXLPsnDTO` The newly created counterpart pseudonym.


Example:
```JavaScript
var psn = {"type":{"name":"PSN1"},"value":"ebad4277-399c-48a1-9c93-a38ced158c1f"};
$http({
    url: TXL_CONFIG.PSNS_HOST + "/rest/addCounterpart",
    method: "POST",
    headers: headers,
    data: psn
}).success(function (counterpartPsn) {
    console.log(counterpartPsn);
});
```
#### POST Create pseudonym tuple
Generate a new pseudonym tuple `(PSN1, PSN2) = (TXLPsnDTO, TXLPsnDTO)`.

> **Resource:** `/rest/createTuple`

> **Method:** `POST` 

> **Parameter:** 

> **Returns:** `TXLPsnTupleDTO` The tuple `(PSN1, PSN2) = (TXLPsnDTO, TXLPsnDTO)`.


Example:
```JavaScript
$http({
    url: TXL_CONFIG.PSNS_HOST + "/rest/createTuple",
    method: "POST",
    headers: headers
}).success(function (psnTuple) {
    console.log(psnTuple.psn1);
    console.log(psnTuple.psn2);
});
```

#### POST Create pseudonym
Create a new **non persisted** pseudonym. Generate a new transient pseudonym of the given type. The created psn will not get persisted. To persist the created pseudonym it must be associated with a counterpart. 
That is, `addCounterpart` must be called for the pseudonym.

> **Resource:** `/rest/createPsn`

> **Method:** `POST` 

> **Parameter:**  `TXLPsnTypeDTO` typeDto

> **Returns:** `TXLPsnDTO` the transient pseudonym.


Example:
```JavaScript
var psnType = {"name":"PSN1"};
$http({
    url: TXL_CONFIG.PSNS_HOST + "/rest/createPsn",
    method: "POST",
    headers: headers,
    data: psnType
}).success(function (psn) {
    console.log(psn);
});
```
### VDAT

#### POST Fetch case by pseudonym

Fetch a case that has the given pseudonym.

> **Resource:** `/rest/case/caseWithPsn`

> **Method:** `POST` 

> **Parameter:** `TXLPsnDTO ` the pseudonym of the case

> **Returns:** `TXLCaseDTO` the case object


Example:
```JavaScript
var casePsn = {"type":{"name":"PSN1"},"value":"ebad4277-399c-48a1-9c93-a38ced158c1f"};
$http({
    url: TXL_CONFIG.VDAT_HOST + "/rest/case/caseWithPsn",
    method: 'POST',
    headers: headers,
    data: casePsn
}).success(function (txlCase) {
    console.log(txlCase);
});
```

#### POST Create a new case

Create a new case that will have the given pseudonym.

> **Resource:** `/rest/case/add`

> **Method:** `POST` 

> **Parameter:** `TXLPsnDTO` the pseudonym for the case.

> **Returns:** `TXLCaseDTO` the case object


Example:
```JavaScript
var casePsn = {"type":{"name":"PSN2"},"value":"ebcf1234-abcd-1234-9c00-a45ced166c1f"};
$http({
    url: TXL_CONFIG.VDAT_HOST + "/rest/case/add",
    method: "POST",
    headers: headers,
    data: casePsn
}).success(function (txlCase) {
    console.log(txlCase);
});
```

#### POST Update a case

Update an existing case.

> **Resource:** `/rest/case/update`

> **Method:** `POST` 

> **Parameter:** `TXLCaseDTO` the case to update, which must include an ID.

> **Returns:** `TXLCaseDTO` the updated case object


Example:
```JavaScript
$http({
url: TXL_CONFIG.VDAT_HOST + "/rest/case/update",
    method: "POST",
    headers: headers,
    data: txlCase
}).success(function (updatedCase) {
    console.log(updatedCase);
});
```

#### POST Fetch user assigned cases

Fetch a list of cases that has the are assigned to the user.

> **Resource:** `/rest/case/assignedCases`

> **Method:** `POST` 

> **Parameter:** `TXLUserDTO` the user from whom we want to fetch the his/her assigned cases.

> **Returns:** `TXLCaseDTO[]` array of cases.


Example:
```JavaScript
var user = {"id": "1234"};
$http({
    url: TXL_CONFIG.VDAT_HOST + "/rest/case/assignedCases",
    method: "POST",
    headers: headers,
    data: user
}).success(function (cases) {
    for (var i = 0; i < cases.length; i++) { 
        console.log(cases[i]);
    }
});
```

#### GET Options for Type

Fetch `Map<String, TXLTypeOptionDTO[]>`  of options for Type.

> **Resource:** `/rest/type/option`

> **Method:** `GET` 

> **Parameter:** `String typeName` the name of the TXLType we want to fetch its options.

> **Returns:** `Map<String, TXLTypeOptionDTO[]>` mapping `typeName -> typeOptions[]`


Example:
```JavaScript
$http({
    url: TXL_CONFIG.VDAT_HOST + "/rest/type/option",
    method: "GET",
    params: {"type": [TXL_TYPE.CASE_TYPE, TXL_TYPE.CASE_TEST_ENVIRONMENT]}
}).then(function (result) {
    var caseTypesOptions = result.data[TXL_TYPE.CASE_TYPE];
    for (var i = 0; i < caseTypesOptions.length; i++) { 
        console.log(caseTypesOptions[i]);
    }
    var testEnvironmentOptions = result.data[TXL_TYPE.CASE_TEST_ENVIRONMENT];
    for (var i = 0; i < testEnvironmentOptions.length; i++) { 
        console.log(testEnvironmentOptions[i]);
    }
});
```
