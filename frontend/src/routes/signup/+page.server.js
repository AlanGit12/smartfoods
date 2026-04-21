import { redirect } from '@sveltejs/kit';
import auth from '$lib/server/auth.service.js';

export const actions = {
  signup: async ({ request, cookies }) => {
    const data = await request.formData();
    const email = data.get('email');
    const password = data.get('password');
    const firstName = data.get('firstName');
    const lastName = data.get('lastName');

    try {
      // Call the auth.js signup function - it handles cookie setting
      await auth.signup(email, password, firstName, lastName, cookies);
    } catch (error) {
      console.error('Signup error:', error);
      return {
        error: 'Signup failed. Please try again.'
      };
    }
    
    // If we get here, signup was successful - redirect
    throw redirect(303, '/');
  }
};
