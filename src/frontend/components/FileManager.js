import React, { useState } from 'react';
import {
    createFile, deleteFile, deleteDuplicates, keywordSearch,
    countWords, loadFilesFromDirectory, writeFile
} from '../services/FileManagerService';
import './FileManager.css';

const FileManager = () => {
    const [files, setFiles] = useState([]);
    const [keyword, setKeyword] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [wordCountResults, setWordCountResults] = useState([]);
    const [directory, setDirectory] = useState('');
    const [message, setMessage] = useState('');
    const [isDirectoryLoaded, setIsDirectoryLoaded] = useState(false);

    const handleLoadFilesFromDirectory = async () => {
        try {
            const encodedDirectoryPath = encodeURIComponent(directory);
            const response = await loadFilesFromDirectory(encodedDirectoryPath);
            const fileList = response.data.map(file => ({
                fileName: file.fileName,
                wordCount: file.wordCount,
                charCount: file.charCount
            }));
            setFiles(fileList);
            setMessage('Files loaded successfully from the directory');
            setIsDirectoryLoaded(true);
        } catch (error) {
            setMessage('Error loading files from the directory');
            console.error('Error loading files from the directory:', error);
        }
    };

    const handleCreateFile = async (fileName, fileContent) => {
        try {
            await createFile(fileName, fileContent);
            setMessage(`File ${fileName} created successfully`);
            handleLoadFilesFromDirectory();
        } catch (error) {
            setMessage(`Error creating file ${fileName}: ${error.response?.data?.message || error.message}`);
            console.error('Error creating file:', error);
        }
    };

    const handleDeleteFile = async (fileName) => {
        try {
            await deleteFile(fileName);
            setMessage(`File ${fileName} deleted successfully`);
            handleLoadFilesFromDirectory();
        } catch (error) {
            setMessage(`Error deleting file ${fileName}: ${error.response?.data?.message || error.message}`);
            console.error('Error deleting file:', error);
        }
    };

    const handleDeleteDuplicates = async () => {
        try {
            await deleteDuplicates();
            setMessage('Duplicates deleted successfully');
            handleLoadFilesFromDirectory();
        } catch (error) {
            setMessage(`Error deleting duplicates: ${error.response?.data?.message || error.message}`);
            console.error('Error deleting duplicates:', error);
        }
    };

    const handleKeywordSearch = async () => {
        try {
            const response = await keywordSearch(keyword);
            setSearchResults(response.data);
            if (response.data.length === 0) {
                setMessage('No files found containing the keyword');
            } else {
                setMessage('Search completed');
            }
        } catch (error) {
            setMessage(`Error searching keyword: ${error.response?.data?.message || error.message}`);
            console.error('Error searching keyword:', error);
        }
    };

    const handleCountWords = async (fileName, numThreads) => {
        try {
            const response = await countWords(fileName, numThreads);
            if (Array.isArray(response.data)) {
                setWordCountResults(response.data.map((entry) => {
                    const [word, count] = entry.split(': ');
                    return { word, count: parseInt(count, 10) };
                }));
                setMessage('Word count completed');
            } else {
                throw new Error('Invalid response format');
            }
        } catch (error) {
            setMessage(`Error counting words: ${error.response?.data?.message || error.message}`);
            console.error('Error counting words:', error);
        }
    };

    const handleWriteFile = async (fileName, content) => {
        try {
            await writeFile(fileName, content);
            setMessage(`Content written to file ${fileName} successfully`);
            handleLoadFilesFromDirectory();
        } catch (error) {
            setMessage(`Error writing to file ${fileName}: ${error.response?.data?.message || error.message}`);
            console.error('Error writing to file:', error);
        }
    };

    return (
        <div className="file-manager">
            <h1>File Manager</h1>
            {message && <p className="message">{message}</p>}

            <div>
                <h2>Select Directory</h2>
                <p>First Step! Enter the path of the directory to load the files from.</p>
                <input
                    type="text"
                    value={directory}
                    onChange={(e) => setDirectory(e.target.value)}
                    placeholder="C:\Users\User\OneDrive\Projects\FileManagerFS"
                />
                <button onClick={handleLoadFilesFromDirectory}>Load Files from Directory</button>
            </div>

            <div>
                <h2>Files</h2>
                <p>List of files in the selected directory along with their word and character counts.</p>
                {files && files.length > 0 ? (
                    <ul>
                        {files.map((file, index) => (
                            <li key={index}>
                                <strong>{file.fileName}</strong>
                                <div>Word Count: {file.wordCount}</div>
                                <div>Character Count: {file.charCount}</div>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No files found</p>
                )}
            </div>

            <div>
                <h2>Create File</h2>
                <p>Create a new file with the specified name, file type, and content.</p>
                <input type="text" placeholder="newFile.txt" id="fileName" disabled={!isDirectoryLoaded} />
                <textarea placeholder="File Content" id="fileContent" disabled={!isDirectoryLoaded} />
                <button onClick={() => handleCreateFile(document.getElementById('fileName').value, document.getElementById('fileContent').value)} disabled={!isDirectoryLoaded}>Create File</button>
            </div>

            <hr />

            <div>
                <h2>Delete File</h2>
                <p>Delete a file with the specified name.</p>
                <input type="text" placeholder="File Name.txt to Delete" id="deleteFileName" disabled={!isDirectoryLoaded} />
                <button onClick={() => handleDeleteFile(document.getElementById('deleteFileName').value)} disabled={!isDirectoryLoaded}>Delete File</button>
            </div>

            <hr />

            <div>
                <h2>Delete Duplicates</h2>
                <p>Delete duplicate files in the selected directory.</p>
                <button onClick={handleDeleteDuplicates} disabled={!isDirectoryLoaded}>Delete Duplicates</button>
            </div>

            <hr />

            <div>
                <h2>Keyword Search</h2>
                <p>Search for files containing the specified keyword.</p>
                <input type="text" placeholder="Keyword" value={keyword} onChange={(e) => setKeyword(e.target.value)} disabled={!isDirectoryLoaded} />
                <button onClick={handleKeywordSearch} disabled={!isDirectoryLoaded}>Search</button>
                {searchResults.length > 0 ? (
                    <div>
                        <h3>Search Results:</h3>
                        <ul>{searchResults.map((result, index) => <li key={index}>{result}</li>)}</ul>
                    </div>
                ) : (
                    <p>No files found containing the keyword</p>
                )}
            </div>

            <hr />

            <div>
                <h2>Count Words in File</h2>
                <p>Count the number of occurrences of each word in the specified file using multiple threads.</p>
                <input type="text" placeholder="File Name" id="countFileName" disabled={!isDirectoryLoaded} />
                <input type="number" placeholder="Number of Threads" id="numThreads" disabled={!isDirectoryLoaded} />
                <button onClick={() => handleCountWords(document.getElementById('countFileName').value, document.getElementById('numThreads').value)} disabled={!isDirectoryLoaded}>Count Words</button>
                {wordCountResults.length > 0 && (
                    <div>
                        <h3>Word Count Results:</h3>
                        <ul>
                            {wordCountResults.map((result, index) => (
                                <li key={index}>{result.word}: {result.count}</li>
                            ))}
                        </ul>
                    </div>
                )}
            </div>

            <hr />

            <div>
                <h2>Write to File's End</h2>
                <p>Append new text to the end of a file.</p>
                <input type="text" placeholder="File Name.txt" id="writeFileName" disabled={!isDirectoryLoaded} />
                <textarea placeholder="Content to write" id="writeContent" disabled={!isDirectoryLoaded} />
                <button onClick={() => handleWriteFile(document.getElementById('writeFileName').value, document.getElementById('writeContent').value)} disabled={!isDirectoryLoaded}>Write to File</button>
            </div>
        </div>
    );
};

export default FileManager;