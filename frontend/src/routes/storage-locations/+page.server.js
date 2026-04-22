import axios from 'axios';
const API_BASE_URL = process.env.API_BASE_URL;

export async function load({ locals }) {
    const jwt_token = locals.jwt_token;
    if (!jwt_token) return { locations: [] };

    try {
        const response = await axios.get(`${API_BASE_URL}/api/storage-locations`, {
            headers: { Authorization: 'Bearer ' + jwt_token }
        });
        return { locations: response.data };
    } catch (e) {
        console.error('Fehler beim Laden:', e);
        return { locations: [] };
    }
}

export const actions = {
    createLocation: async ({ request, locals }) => {
        const jwt_token = locals.jwt_token;
        if (!jwt_token) return { success: false };

        const data = await request.formData();
        const location = {
            name: data.get('name'),
            storageType: data.get('storageType'),
            icon: data.get('icon')
        };

        try {
            await axios.post(`${API_BASE_URL}/api/storage-locations`, location, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + jwt_token
                }
            });
            return { success: true };
        } catch (e) {
            console.error('Fehler beim Erstellen:', e);
            return { success: false };
        }
    },

    deleteLocation: async ({ request, locals }) => {
        const jwt_token = locals.jwt_token;
        if (!jwt_token) return { success: false };

        const data = await request.formData();
        const id = data.get('id');

        try {
            await axios.delete(`${API_BASE_URL}/api/storage-locations/${id}`, {
                headers: { Authorization: 'Bearer ' + jwt_token }
            });
            return { success: true };
        } catch (e) {
            console.error('Fehler beim Löschen:', e);
            return { success: false };
        }
    }
};
