import axios from 'axios';
const API_BASE_URL = process.env.API_BASE_URL;

export async function load({ locals }) {
    const jwt_token = locals.jwt_token;
    if (!jwt_token) return { items: [], locations: [] };

    try {
        const [itemsRes, locationsRes] = await Promise.all([
            axios.get(`${API_BASE_URL}/api/inventory`, {
                headers: { Authorization: 'Bearer ' + jwt_token }
            }),
            axios.get(`${API_BASE_URL}/api/storage-locations`, {
                headers: { Authorization: 'Bearer ' + jwt_token }
            })
        ]);
        return {
            items: itemsRes.data,
            locations: locationsRes.data
        };
    } catch (e) {
        console.error('Fehler beim Laden:', e);
        return { items: [], locations: [] };
    }
}

export const actions = {
    createItem: async ({ request, locals }) => {
        const jwt_token = locals.jwt_token;
        if (!jwt_token) return { success: false };

        const data = await request.formData();
        const item = {
            name: data.get('name'),
            unit: data.get('unit'),
            totalAmount: data.get('totalAmount') ? parseFloat(data.get('totalAmount')) : null,
            quantity: parseInt(data.get('quantity')) || 1,
            expiryDate: data.get('expiryDate'),
            purchasePrice: parseFloat(data.get('purchasePrice')),
            storageLocationId: data.get('storageLocationId')
        };

        try {
            await axios.post(`${API_BASE_URL}/api/inventory`, item, {
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

    consumeItem: async ({ request, locals }) => {
        const jwt_token = locals.jwt_token;
        if (!jwt_token) return { success: false };

        const data = await request.formData();
        const id = data.get('id');
        const amount = data.get('amount') || 1;

        try {
            await axios.patch(`${API_BASE_URL}/api/inventory/${id}/consume?amount=${amount}`, {}, {
                headers: { Authorization: 'Bearer ' + jwt_token }
            });
            return { success: true };
        } catch (e) {
            return { success: false };
        }
    },

    wasteItem: async ({ request, locals }) => {
        const jwt_token = locals.jwt_token;
        if (!jwt_token) return { success: false };

        const data = await request.formData();
        const id = data.get('id');

        try {
            await axios.patch(`${API_BASE_URL}/api/inventory/${id}/waste`, {}, {
                headers: { Authorization: 'Bearer ' + jwt_token }
            });
            return { success: true };
        } catch (e) {
            return { success: false };
        }
    }
};
