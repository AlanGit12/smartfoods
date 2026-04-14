import axios from "axios";
// Load environment variables from .env file for local development
import 'dotenv/config'; 
const AUTH0_DOMAIN = process.env.AUTH0_DOMAIN;
const AUTH0_CLIENT_ID = process.env.AUTH0_CLIENT_ID

// Auth0 signup endpoint documentation: see https://auth0.com/docs/libraries/custom-signup#using-the-api
async function signup(
  email,
  password,
  firstName = null,
  lastName = null,
  cookies
) {
  var options = {
    method: "post",
    url: `https://${AUTH0_DOMAIN}/dbconnections/signup`,
    data: {
      client_id: AUTH0_CLIENT_ID,
      email: email,
      password: password,
      connection: "Username-Password-Authentication",
      // you can set any of these properties as well if needed
      // username: "johndoe", // if not provided, email will be used as username for login. if provided, username has to be validated (must not already exist)
      // given_name: "John",
      // family_name: "Doe",
      // nickname: "Johnny", // if not provided, the part before the @ of the e-mail address will be used
      // name: "John Doe",
      // picture: "http://example.org/jdoe.png",
    },
  };

  if (firstName && firstName.length > 0) {
    options.data.given_name = firstName;
  }

  if (lastName && lastName.length > 0) {
    options.data.family_name = lastName;
  }

  try {
    const response = await axios(options);
    
    // wait 2 seconds. Explanation: The user roles are set automatically on signup,
    // but we have to wait a short amount of time to make sure that the roles are
    // stored in the database of auth0. Otherwise the roles may not be in the
    // userinfo object on the first login.
    await new Promise(resolve => setTimeout(resolve, 2000));
    
    return await login(email, password, cookies);
  } catch (error) {
    throw error;
  }
}

async function login(username, password, cookies) {
  var options = {
    method: "post",
    url: `https://${AUTH0_DOMAIN}/oauth/token`,
    data: {
      grant_type: "password",
      username: username,
      password: password,
      audience: `https://${AUTH0_DOMAIN}/api/v2/`,
      scope: "openid profile email",
      client_id: AUTH0_CLIENT_ID,
    },
  };

  try {
    const response = await axios(options);
    const { id_token, access_token } = response.data;

    console.log(id_token);
    
    // Get user info and set cookies
    const userInfo = await getUserInfo(access_token);
    
    // Set cookies via SvelteKit cookies API
    cookies.set('jwt_token', id_token, {
      path: '/',
      maxAge: 60 * 60 * 24 * 7, // 7 days
      sameSite: 'lax',
      httpOnly: true,
      secure: process.env.NODE_ENV === 'production'
    });
    
    cookies.set('user_info', JSON.stringify(userInfo), {
      path: '/',
      maxAge: 60 * 60 * 24 * 7, // 7 days
      sameSite: 'lax',
      httpOnly: true,
      secure: process.env.NODE_ENV === 'production'
    });
    
    return { success: true };
  } catch (error) {
    throw error;
  }
}

async function getUserInfo(access_token) {
  var options = {
    method: "get",
    url: `https://${AUTH0_DOMAIN}/oauth/userinfo`,
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + access_token,
    },
  };

  try {
    const response = await axios(options);
    return response.data;
  } catch (error) {
    throw error;
  }
}

const auth = {
  signup,
  login,
};

export default auth;
