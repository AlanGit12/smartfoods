import axios from 'axios';
const API_BASE_URL = process.env.API_BASE_URL;

export async function load({ locals }) {
    const jwt_token = locals.jwt_token;
    if (!jwt_token) return { templates: [] };

    try {
        const response = await axios.get(`${API_BASE_URL}/api/item-templates`, {
            headers: { Authorization: 'Bearer ' + jwt_token }
        });
        return { templates: response.data };
    } catch (e) {
        console.error('Fehler beim Laden:', e);
        return { templates: [] };
    }
}

export const actions = {
    createTemplate: async ({ request, locals }) => {
        const jwt_token = locals.jwt_token;
        if (!jwt_token) return { success: false };

        const data = await request.formData();
        const template = {
            name: data.get('name'),
            defaultUnit: data.get('defaultUnit'),
            defaultAmount: parseFloat(data.get('defaultAmount')),
            defaultPrice: parseFloat(data.get('defaultPrice'))
        };

        try {
            await axios.post(`${API_BASE_URL}/api/item-templates`, template, {
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

    deleteTemplate: async ({ request, locals }) => {
        const jwt_token = locals.jwt_token;
        if (!jwt_token) return { success: false };

        const data = await request.formData();
        const id = data.get('id');

        try {
            await axios.delete(`${API_BASE_URL}/api/item-templates/${id}`, {
                headers: { Authorization: 'Bearer ' + jwt_token }
            });
            return { success: true };
        } catch (e) {
            console.error('Fehler beim Löschen:', e);
            return { success: false };
        }
    },

    updateTemplate: async ({ request, locals }) => {
        const jwt_token = locals.jwt_token;
        if (!jwt_token) return { success: false };

        const data = await request.formData();
        const id = data.get('id');
        const template = {
            name: data.get('name'),
            defaultUnit: data.get('defaultUnit'),
            defaultAmount: parseFloat(data.get('defaultAmount')),
            defaultPrice: parseFloat(data.get('defaultPrice'))
        };

        try {
            await axios.patch(`${API_BASE_URL}/api/item-templates/${id}`, template, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + jwt_token
                }
            });
            return { success: true };
        } catch (e) {
            console.error('Fehler beim Bearbeiten:', e);
            return { success: false };
        }
    }
};
