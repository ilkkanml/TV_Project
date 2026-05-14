# M4 Subscription Infrastructure

## Status

M4 is active.

## Goal

Create the first subscription and device activation direction for Nexora TV.

## Access Model

Initial model:

- Device identity
- Activation password
- Subscription status
- Session persistence

## User Flow

1. App shows device identity.
2. User enters activation password.
3. Backend validates access.
4. Active subscription opens the app.
5. Expired or disabled device is blocked.

## Admin Needs

- Add device
- Set password
- Set expiration date
- Enable or disable device
- View subscription status

## Next Focus

Backend schema and Android auth flow.
