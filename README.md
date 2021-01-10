# "User Account Service" demo project

This service uses HTTP Basic authentication.

## Register user account

### Request

**POST** `http://example.com/api/user-accounts`

```json
{
  "userAccount": {
    "email": "bob@example.com"
  },
  "password": "123"
}
```

### Response

**HTTP 201 Created** : succeeded

```json
{
  "ok": true,
  "error": null,
  "fieldValidationErrors": null,
  "data": {
    "id": 1,
    "email": "bob@example.com"
  }
}
```

**HTTP 409 Conflict** : already registered

```json
{
  "ok": false,
  "error": "Email has already been registered: bob@example.com",
  "fieldValidationErrors": null,
  "data": null
}
```

**422 Unprocessable Entity** : validation errors

```json
{
  "ok": false,
  "error": "",
  "fieldValidationErrors": {
    "email": "the email must be a valid email address"
  },
  "data": null
}
```

## Authenticate user account

### Request

**POST** `http://example.com/api/authentication-requests`

```json
{
  "email": "bob@example.com",
  "password": "123"
}
```

### Response

**HTTP 200 OK** : succeeded

```json
{
  "ok": true,
  "error": null,
  "fieldValidationErrors": null,
  "data": {
    "id": 1,
    "email": "bob@example.com"
  }
}
```

**422 Unprocessable Entity** : authentication failed

```json
{
  "ok": false,
  "error": "User account authentication attempt failed.",
  "fieldValidationErrors": null,
  "data": null
}
```

**422 Unprocessable Entity** : validation errors

```json
{
  "ok": false,
  "error": "",
  "fieldValidationErrors": {
    "email": "the email must be a valid email address"
  },
  "data": null
}
```