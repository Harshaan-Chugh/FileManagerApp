# File Manager

FileManager is a file management application developed using Java Spring Boot for the backend and React for the frontend. It provides functionalities to load files from a directory, create files, delete files, delete duplicate files, search for keywords within files, and display a file's top 10 frequent words using multithreading.

Scroll to the bottom for a demo of its usage!

## Features

- **Load Files**: Load text files from a specified directory and display their word and character counts.
- **Create File**: Create a new file with specified content in the directory.
- **Write File**: Append content to an existing file.
- **Delete File**: Delete a specified file from the directory.
- **Delete Duplicates**: Delete duplicate files based on their content.
- **Keyword Search**: Search for files containing a specified keyword.
- **Display Top 10 Words**: Rapidly computes and displays the top 10 frequently appearing words in a specified file using a multithreaded approach. Allows user to select thread count.

## Prerequisites

- Java 11 or higher
- Node.js and npm
- Maven

## Getting Started
The File Manager Application is configured to be run locally for security and ease of use.
### Backend Setup

1. **Clone the repository**:
    ```sh
    git clone https://github.com/Harshaan-Chugh/FileManagerApp.git
    cd FileManagerFS
    ```

2. **Build the project**:
    ```sh
    mvn clean install
    ```

3. **Run the Spring Boot application**:
    ```sh
    mvn spring-boot:run
    ```

### Frontend Setup

1. **Navigate to the root directory**:
    ```sh
    cd frontend
    ```

2. **Install the dependencies**:
    ```sh
    npm install
    ```

3. **Start the React application**:
    ```sh
    npm start
    ```

### API Endpoints
Use these API Endpoints for testing in Postman or to better understand the functionality of the program. 
- **Load Files**
    - URL: `/api/files/loadFiles`
    - Method: `GET`
    - Query Parameters: `directoryPath` (string)
    - Response: List of file details including file name, word count, and character count

- **Create File**
    - URL: `/api/files/createFile`
    - Method: `POST`
    - Query Parameters: `fileName` (string), `fileContent` (string)
    - Response: Success or error message

- **Write File**
    - URL: `/api/files/writeFile`
    - Method: `POST`
    - Query Parameters: `fileName` (string), `fileContent` (string)
    - Response: Success or error message

- **Delete File**
    - URL: `/api/files/deleteFile`
    - Method: `DELETE`
    - Query Parameters: `fileName` (string)
    - Response: Success or error message

- **Delete Duplicates**
    - URL: `/api/files/deleteDuplicates`
    - Method: `DELETE`
    - Response: Success or error message

- **Keyword Search**
    - URL: `/api/files/keywordSearch`
    - Method: `GET`
    - Query Parameters: `keyword` (string)
    - Response: List of file names containing the keyword

- **Count Words**
    - URL: `/api/files/countWords`
    - Method: `GET`
    - Query Parameters: `fileName` (string), `numThreads` (int)
    - Response: List of word counts

## Project Structure

### Backend Classes

- **EditableFile**: Represents an editable file with functionalities to read, write, and count words and characters.
- **FileManager**: Manages a list of `EditableFile` instances.
- **FileManagerController**: REST controller for managing file operations.
- **FileManagerService**: Service for managing files in a specified directory.
- **MultithreadedWordCounter**: Multithreaded word counter that counts words in a file using a specified number of threads.
- **WordCounterService**: Service for the word counting operation.
- **WebConfig** Allows for Cross-Origin Resource Sharing (CORS) to enable communication with the React frontend, since they have different origins.
- **FileManagerApplication**: Main application class for the FileManager application.

### Frontend

- **FileManager**: Main React component that provides the user interface for managing files.
- **FileManagerService**: Contains functions to make API calls to the backend.

## Running Tests

To run tests for the backend, use the following command:
```sh
mvn test
```

For frontend tests, use:
```sh
npm test
```

## Contributing

Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License.

-----------------------------------------------
Note: Number of threads used was intentionally backspaced for clarity via placeholder text.
Also: Excuse the blurriness of the screenshot.
![LiveDemo.png](demo.png)