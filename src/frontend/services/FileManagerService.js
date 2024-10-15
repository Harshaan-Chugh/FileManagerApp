import axios from 'axios';

const API_URL = 'https://your-heroku-app.herokuapp.com/api/files'; //local url is http://localhost:8080/api/files

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
        return await axios.post(`${API_URL}/createFile`, null, {params: {fileName, fileContent}});
    } catch (error) {
        console.error('Error creating file:', error);
        throw error;
    }
};

export const deleteFile = async (fileName) => {
    try {
        return await axios.delete(`${API_URL}/deleteFile`, {params: {fileName}});
    } catch (error) {
        console.error('Error deleting file:', error);
        throw error;
    }
};

export const deleteDuplicates = async () => {
    try {
        return await axios.delete(`${API_URL}/deleteDuplicates`);
    } catch (error) {
        console.error('Error deleting duplicates:', error);
        throw error;
    }
};

export const keywordSearch = async (keyword) => {
    try {
        return await axios.get(`${API_URL}/keywordSearch`, {params: {keyword}});
    } catch (error) {
        console.error('Error searching keyword:', error);
        throw error;
    }
};

export const countWords = async (fileName, numThreads) => {
    try {
        return await axios.get(`${API_URL}/countWords`, {params: {fileName, numThreads}});
    } catch (error) {
        console.error('Error counting words:', error);
        throw error;
    }
};

export const writeFile = async (fileName, content) => {
    try {
        return await axios.post(`${API_URL}/writeFile`, null, {params: {fileName, fileContent: content}});
    } catch (error) {
        console.error('Error writing to file:', error);
        throw error;
    }
};