## Usage

Build the image:

```
docker build -t apache_definitivo:1.0 .
```

Run the image to use it:

```
docker run -d -p 9090:80 -v $(pwd)/scripts:/usr/local/apache2/cgi-bin -v $(pwd)/html:/usr/local/apache2/htdocs apache_definitivo:1.0
```
