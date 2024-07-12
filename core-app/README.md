# core-app

The core app

## Configuration

The implementing app passes in a "-config" parameter which provides the path to the configuration file that is loaded in.

The configuration file has the following format:

```json
{
    "serviceName": {
        "package": "com.example",
        "attribute": "value"
    }
}
```

The top level attribute names match the names of the service.
