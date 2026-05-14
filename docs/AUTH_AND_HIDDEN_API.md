# Nexora TV — Auth and Hidden API Direction

## Goal

Users must register and log in inside the Nexora TV app without seeing or accessing the owner's website, admin panel, or database.

## Architecture

App communicates only with a protected backend API.

The app must never connect directly to the database.

## User-Facing App Screens

- Register
- Login
- Forgot Password
- Device Activation

## Registration Fields

- Email
- Username
- Password

## Login Fields

- Username or email
- Password

## Forgot Password

Preferred TV-friendly flow:

- User enters email
- Backend sends a 6 digit recovery code
- User enters code in the TV app
- User creates a new password

## Hidden Backend Rule

The website and admin panel must not be visible to normal app users.

The backend API may use a separate protected endpoint such as an API subdomain or proxy layer.

## Security Rules

- No direct database access from the app
- No database credentials inside the app
- Passwords stored only as hashes
- Device identity linked to user account
- Subscription status validated by backend
- Admin controls stay private

## Admin Needs

- View users
- View devices
- Enable or disable accounts
- Set subscription expiration
- Reset password if needed
- Manage activation keys
