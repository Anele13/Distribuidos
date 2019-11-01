## Usage

### Build the image:

```
docker build -t `*tag*` .
```

### Run the image to use it:

```
docker run -d -p 9090:80 -v $(pwd)/scripts:/usr/local/apache2/cgi-bin -v $(pwd)/html:/usr/local/apache2/htdocs `*tag*`
```

### Running:

```
localhost:9090/login y arranca todo!
```
