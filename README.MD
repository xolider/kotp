# kotp

kotp is a Kotlin/JVM library for generating and verifying One-Time Password (OTP) codes,
suitable for 2FA (Two-Factor Authentication) implementations.

## Features

* **HMAC-based OTP (HOTP)**: Generate and verify OTP codes based on a counter.
* **Time-based OTP (TOTP)**: Generate and verify OTP codes based on time.
* **Configurable**: Define code length, time step and hashing algorithm
* **Lightweight**: Minimal dependencies and easy integration

## Installation

Add the library to your project by including the following dependency:

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("dev.vicart:kotp:<version>")
}
```

### Maven

```xml
<dependency>
    <groupId>dev.vicart</groupId>
    <artifactId>kotp</artifactId>
    <version>...</version>
</dependency>
```

## Usage

### TOTP (Time-based OTP)

#### Generation

```kotlin
val totpGenerator = TOTPGenerator()
val code = totpGenerator.generateCode("base32-encoded key")
```

#### Verification

```kotlin
val totpVerifier = TOTPVerifier()
val verified = totpVerifier.verify("base32-encoded key", "OTP code")
```

_Note:_ kotp automatically checks for previous and next time step on verification for error detection

### HOTP (HMAC-based OTP)

#### Generation

```kotlin
val hotpGenerator = HOTPGenerator()
val code = hotpGenerator.generateCode("base32-encoded key", 0)
```

#### Verification

```kotlin
val hotpVerifier = HOTPVerifier()
val verified = hotpVerifier.verify("base32-encoded key", "OTP code", 0)
```

## Configuration

The library provides various options for customization:
* `codeLength`: Number of digits in the OTP (default: 6)
* `period`: Duration (in seconds) for which a TOTP is valid (Default: 30 seconds)
* `algorithm`: Hashing algorithm for OTP generation (Default: SHA1)