package com.function;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.storage.blob.*;

/**
 * Azure Functions with Azure Blob trigger.
 */
public class JavaBlobTrigger {
        /**
         * This function will be invoked when a new or updated blob is detected at the
         * specified path. The blob contents are provided as input to this function.
         */
        @FunctionName("JavaBlobTrigger")
        public void run(
                        @BlobTrigger(name = "file", dataType = "binary", path = "uploads/{name}", connection = "MyStorageAccountAppSetting") byte[] content,
                        @BindingName("name") String filename,
                        final ExecutionContext context) {

                context.getLogger().info("Name: " + filename + " Size: "
                                + content.length
                                + " bytes");

                // Create a ManagedIdentityCredential object
                ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().build();

                // // Azure default Credential
                // DefaultAzureCredential defaultAzureCredential = new DefaultAzureCredentialBuilder().build();

                // get value of MyStorageAccountConnectionString from azure app settings
                String myStorageAccountUrl = System.getenv("MyStorageAccountEndPoint");

                context.getLogger().info("MyStorageAccountEndPoint: " + myStorageAccountUrl);

                // Create a BlobServiceClient object with a credential
                BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                                .endpoint(myStorageAccountUrl)
                                .credential(managedIdentityCredential)
                                .buildClient();

                // Get a reference to a container
                BlobContainerClient sourceContainer = blobServiceClient.getBlobContainerClient("uploads");
                BlobContainerClient targetContainer = blobServiceClient.getBlobContainerClient("success");

                BlobClient sourceBlob = sourceContainer.getBlobClient(filename);
                BlobClient targetBlob = targetContainer.getBlobClient(filename);
                targetBlob.copyFromUrl(sourceBlob.getBlobUrl());
                sourceBlob.delete();

                context.getLogger().info("File Name " + filename + "copied to success container");
        }
}
