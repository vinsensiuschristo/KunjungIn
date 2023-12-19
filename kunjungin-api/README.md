### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/vinsensiuschristo/KunjungIn.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Run this command to ensure that all tables in the database have been created
   ```sh
   npx prisma generate
   ```
3. Create your Credentials Key from Google Cloud Platform, and get Google Client ID and Google Secret ID
4. Enter your Google Client ID, Google Secret ID, Database URL, Port and JWTSecret in `.env`
   ```js
   PORT='YOUR PORT'
   GOOGLE_CLIENT_ID='YOUR GOOGLE PUBLIC KEY'
   GOOGLE_CLIENT_SECRET='YOUR GOOGLE SECRET KEY'
   DATABASE_URL='YOUR DATABASE URL'
   JWTSECRET='YOUR JWTSECRET KEY'

   ```

_______