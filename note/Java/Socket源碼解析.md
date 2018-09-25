<h1>一、实现逻辑</h1>
<h3>1.参数</h3>
<pre><code>
    /**
     * Various states of this socket.
     */
    private boolean created = false;
    private boolean bound = false;
    private boolean connected = false;
    private boolean closed = false;
    private Object closeLock = new Object();
    private boolean shutIn = false;
    private boolean shutOut = false;<br>
    /**
     * The implementation of this Socket.
     */
    <font style="text-decoration: none;color: red">SocketImpl impl</font> ;

    /**
     * Are we using an older SocketImpl?
     */
    private boolean oldImpl = false;
</code></pre>
<h3>2.构造器</h3>
<ul>
	<li>Unconnected socket</li>
	<pre><code>/**
  * Creates an <font color="red">unconnected socket</font>, with the
  * system-default type of <font color="red">SocketImpl</font>.
  *
  * @since   JDK1.1
  * @revised 1.4
  */
  public Socket() {
	setImpl();
  }
 <br>
 /**
  * Creates an <font color="red">unconnected socket</font>, specifying the type of proxy, if any,
  * that should be used regardless of any other settings.
  * 
  * If there is a security manager, its {@code checkConnect} method
  * is called with the proxy host address and port number
  * as its arguments. This could result in a SecurityException.
  * 
  * Examples:
  * {@code Socket s = new Socket(Proxy.NO_PROXY);} will create a plain socket ignoring any other proxy configuration.
  * {@code Socket s = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("socks.mydom.com", 1080)));}
  * will create a socket connecting through the specified SOCKS proxy
  * server.
  *
  * @param proxy a {@link java.net.Proxy Proxy} object specifying what kind
  *              of proxying should be used.
  * @throws IllegalArgumentException if the proxy is of an invalid type
  *          or {@code null}.
  * @throws SecurityException if a security manager is present and
  *                           permission to connect to the proxy is
  *                           denied.
  * @see java.net.ProxySelector
  * @see java.net.Proxy
  *
  * @since   1.5
  */
  public Socket(Proxy proxy) {
        // Create a copy of Proxy as a security measure
        if (proxy == null) {
            throw new IllegalArgumentException("Invalid Proxy");
        }
        Proxy p = proxy == Proxy.NO_PROXY ? Proxy.NO_PROXY
                                          : sun.net.ApplicationProxy.create(proxy);
        Proxy.Type type = p.type();
        if (type == Proxy.Type.SOCKS || type == Proxy.Type.HTTP) {
            SecurityManager security = System.getSecurityManager();
            InetSocketAddress epoint = (InetSocketAddress) p.address();
            if (epoint.getAddress() != null) {
                checkAddress (epoint.getAddress(), "Socket");
            }
            if (security != null) {
                if (epoint.isUnresolved())
                    epoint = new InetSocketAddress(epoint.getHostName(), epoint.getPort());
                if (epoint.isUnresolved())
                    security.checkConnect(epoint.getHostName(), epoint.getPort());
                else
                    security.checkConnect(epoint.getAddress().getHostAddress(),
                                  epoint.getPort());
            }
            impl = type == Proxy.Type.SOCKS ? new SocksSocketImpl(p)
                                            : new HttpConnectSocketImpl(p);
            impl.setSocket(this);
        } else {
            if (p == Proxy.NO_PROXY) {
                if (factory == null) {
                    impl = new PlainSocketImpl();
                    impl.setSocket(this);
                } else
                    setImpl();
            } else
                throw new IllegalArgumentException("Invalid Proxy");
        }
    }
    <br>
  /**
   * Creates an unconnected Socket with a user-specified
   * SocketImpl.
   * <P>
   * @param impl an instance of a <B>SocketImpl</B>
   * the subclass wishes to use on the Socket.
   *
   * @exception SocketException if there is an error in the underlying protocol,
   * such as a TCP error.
   * @since   JDK1.1
   */
    protected Socket(SocketImpl impl) throws SocketException {
        this.impl = impl;
        if (impl != null) {
            checkOldImpl();
            this.impl.setSocket(this);
        }
    }
	</code></pre>
	<li>Connected socket</li>
</ul>