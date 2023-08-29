# Azure Function App with Blob Trigger using Managed Identity

This repository contains a Java-based Azure Function App that demonstrates the use of Blob Trigger and Managed Identity for authentication.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Configure Azure Resources](#configure-azure-resources)
  - [Set Environment Variables](#set-environment-variables)
- [Deployment](#deployment)
- [Usage](#usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Features

- Uses Azure Functions with Blob Trigger written in Java.
- Leverages Azure Managed Identity for secure, straightforward credential management.

## Prerequisites

- Java Development Kit (JDK) 8 or later.
- Maven.
- Azure CLI or Azure Portal access.
- An Azure Storage Account.
- Azure Functions Core Tools (optional).

## Getting Started

### Clone the Repository

Clone this repository onto your local machine:

```bash
git clone https://github.com/Keayoub/JavaFunctionManagedIdentity.git
```
### Configure Azure Resources

1. **Create an Azure Function App**: Navigate to the Azure Portal and create a new Function App. Make sure to select Java as the runtime stack. Alternatively, you can use the Azure CLI to create the Function App.
  
2. **Create an Azure Storage Account**: If you don't already have one, create an Azure Storage Account. This will be used to store the blobs that trigger your Azure Function.

3. **Enable Managed Identity**: For the Azure Function App you just created, enable Managed Identity. This will allow your Function App to interact securely with other Azure services without storing credentials in code.

### Set Environment Variables

You need to set the `MyStorageAccountConnectionString` environment variable to hold the connection string of your Azure Storage Account. This can be set in the Azure Portal under your Function App's "Configuration" settings. If you prefer using the Azure CLI, you can run the following command:

```bash
az functionapp config appsettings set --name <YourFunctionAppName> --resource-group <YourResourceGroupName> --settings MyStorageAccountConnectionString=<YourStorageAccountConnectionString>
```

### Deployment

To deploy your Azure Function, first navigate to the root directory of your project. Run the following command to package your application:

```bash
mvn clean package
```

Once the package is successfully built, deploy the function app to Azure using the Azure CLI with the following command:

```bash
az functionapp deployment source config-zip -g <YourResourceGroupName> -n <YourFunctionAppName> --src target/azure-functions/<YourFunctionAppName>-java.zip
```

This will deploy the packaged function to the specified function app in Azure. Make sure to replace `<YourResourceGroupName>` and `<YourFunctionAppName>` with your actual resource group name and function app name, respectively.

### Usage

After the deployment is successful, your Azure Function will be live. It will automatically trigger whenever a new blob is uploaded to the specified container in your Azure Storage Account. The function uses Managed Identity for secure authentication with the storage account, eliminating the need for explicit credentials in your code.

### Troubleshooting

- **MalformedURLException**: If you encounter this error, double-check that the `MyStorageAccountConnectionString` environment variable is correctly set in your Azure Function App's settings.
- **Function Not Triggering**: If the Azure Function is not triggering as expected, ensure that your blob container and trigger settings are correctly configured.

### Contributing

If you're interested in contributing to this project, feel free to fork the repository, make your changes, and then create a pull request.

### License

This project is licensed under the MIT License. See the `LICENSE.md` file for more details.

