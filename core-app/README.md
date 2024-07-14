# core-app

The core app

## Configuration

The implementing app passes in a "-config" parameter which provides the path to the configuration file that is loaded in.

The configuration file has the following format:

```json
{
    "com.example": {
        "attribute": "value"
    }
}
```

The top level attribute names specifies the package of the service module that will be loaded in.  This package must specify the package for the single module as only a single configuration can be loaded in and passed to those services.
