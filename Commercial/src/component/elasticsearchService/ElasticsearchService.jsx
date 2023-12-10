import axios from 'axios';
import base_url from '../baseUrl/BaseUrl';

const elasticsearchBaseUrl = `${base_url}/products/fuzzySearch`;

const ElasticsearchService = {
    fuzzySearch: async (approximateProductName) => {
        try {
            const response = await axios.get(`${elasticsearchBaseUrl}/${approximateProductName}`);
            return response.data;
        } catch (error) {
            throw new Error('Error searching products.');
        }
    },
};

export default ElasticsearchService;