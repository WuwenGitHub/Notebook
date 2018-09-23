<h1>进程间通信</h1>
<table>
  <tr>
    <th scope="col">&nbsp;</th>
    <th scope="col">通信方式</th>
    <th scope="col">优缺点</th>
  </tr>
  <tr>
    <th rowspan="11" scope="row">同一机器间</th>
    <td rowspan="2">管道</td>
    <td>只支持半双工通信(单向交替传输)</td>
  </tr>
  <tr>
    <td>只能在父子进程中使用</td>
  </tr>
  <tr>
    <td rowspan="2">FIFO(命名管道)</td>
    <td>只支持半双工通信(单向交替传输)</td>
  </tr>
  <tr>
  	<td>常用于客户-服务器应用程序中(作汇聚点，在客户进程和服务器进程之间传递数据)</td>
  </tr>
  <tr>
    <td rowspan="3">消息队列</td>
    <td>可以独立于读写进程存在，从而避免了 FIFO 中同步管道的打开和关闭时可能产生的困难</td>
  </tr>
  <tr>
  	<td>避免了 FIFO 的同步阻塞问题，不需要进程自己提供同步方法</td>
  </tr>
  <tr>
  	<td>读进程可以根据消息类型有选择地接收消息，而不像 FIFO 那样只能默认地接收</td>
  </tr>
  <tr>
    <td>信号量</td>
    <td>一个计数器，用于对多个进程提供对共享数据对象的访问</td>
  </tr>
  <tr>
    <td rowspan="3">共享存储</td>
    <td>多个进程共享一个给定的存储区(最快的一种IPC)</td>
  </tr>
  <tr>
  	<td>使用信号量来同步对共享存储的访问</td>
  </tr>
  <tr>
  	<td>实现共享内存方式：多个进程将共享文件映射到它们的地址空间</td>
  </tr>
  <tr>
    <th scope="row">不同机器间</th>
    <td>套接字</td>
    <td>可用于不同机器间通信</td>
  </tr>
</table>
<h1>Socket通信</h1>