# xml on client and server

This xml project is aimed to provide a dom model, which can be used on both client (browser) and server side.

Besides dom model, xpath 1.0 api is also supported on both sides.

The ultimate goal is provide the following api supports on both sides.

- xpath 3.1
- xslt 3.0
- xquery 3.1

The dom model in this project is semi thread-safe.

On client side (browser), it is totally thread-safe due to the JavaScript's single-threaded nature.

On server side, it is thread-safe for read-only operations, which means XPath operations can be parallel executed.

For write operations, the <code>Document</code> object needs to be external synchronized. In other words, no any read
and/or other write operations of different thread are allowed during writing process.

TODO