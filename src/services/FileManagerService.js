import axios from 'axios';

const API_URL = 'http://localhost:8080/api/files';

export const loadFilesFromDirectory = async (directoryPath) => {
    try {
        const encodedPath = encodeURIComponent(directoryPath);
        const url = `${API_URL}/loadFiles?directoryPath=${encodedPath}`;
        return await axios.get(url, {withCredentials: true}); // Return the full response
    } catch (error) {
        console.error('Error loading files from directory:', error);
        throw error;
    }
};

export const createFile = async (fileName, fileContent) => {
    try {
        const response = await axios.post(`${API_URL}/createFile`, null, { params: { fileName, fileContent } });
        return response;
    } catch (error) {
        console.error('Error creating file:', error);
        throw error;
    }
};

export const deleteFile = async (fileName) => {
    try {
        const response = await axios.delete(`${API_URL}/deleteFile`, { params: { fileName } });
        return response;
    } catch (error) {
        console.error('Error deleting file:', error);
        throw error;
    }
};

export const deleteDuplicates = async () => {
    try {
        const response = await axios.delete(`${API_URL}/deleteDuplicates`);
        return response;
    } catch (error) {
        console.error('Error deleting duplicates:', error);
        throw error;
    }
};

export const keywordSearch = async (keyword) => {
    try {
        const response = await axios.get(`${API_URL}/keywordSearch`, { params: { keyword } });
        return response;
    } catch (error) {
        console.error('Error searching keyword:', error);
        throw error;
    }
};

export const countWords = async (fileName, numThreads) => {
    try {
        const response = await axios.get(`${API_URL}/countWords`, { params: { fileName, numThreads } });
        return response;
    } catch (error) {
        console.error('Error counting words:', error);
        throw error;
    }
};

export const writeFile = async (fileName, content) => {
    try {
        const response = await axios.post(`${API_URL}/writeFile`, null, { params: { fileName, fileContent: content } });
        return response;
    } catch (error) {
        console.error('Error writing to file:', error);
        throw error;
    }
};