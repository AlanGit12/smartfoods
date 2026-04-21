import { redirect } from '@sveltejs/kit';
import auth from '$lib/server/auth.service.js';

export const actions = {
  default: async ({ request, cookies }) => {
    const data = await request.formData();
    const email = data.get('email');
    const password = data.get('password');

    try {
      // Call the auth.js login function - it handles cookie setting
      await auth.login(email, password, cookies);
    } catch (error) {
      console.error('Login error:', error);
      return {
        error: 'Login failed. Please check your credentials.'
      };
    }
    
    // If we get here, login was successful - redirect to home
    // Using status 302 to ensure a full page load that will refresh all data
    throw redirect(302, '/');
  }
};
