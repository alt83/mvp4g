# Mvp4g vs GWTP (or gwt-platform) #

Mvp4g and GWTP are 2 frameworks that implement the best practices presented by Ray Ryan last year (MVP/Event bus/Dependency Injection). Their approach, however, is a little bit different.

The idea behind Mvp4g is simple: whenever a presenter doesn't know how to do something, it asks the rest of the application to do it via an event. Thus a presenter will fire an event to display its view, ask other presenters to reveal themselves, pass information to other presenters, etc... This means that you have to create a lot of events, so it can work only if creating events and managing them (ie set/control handlers) is easy to do. And this is exactly what Mvp4g aims to do: all you need to create and set handlers is one method and one annotation.

GWTP has another approach. Its event bus is based on GWT Handler Manager so you need some lines of code to create an event (one class and one handler). To solve this issue, GWTP implements technics to reduce the number of events needed. For example, you need only one event (provided by GWTP) for any presenter to ask the rest of the application to display its view. You will also tend to use a history token (via the place service) instead of events to ask other presenter to reveal itself.

Here is a comparison of how the 2 frameworks deal with the main features needed for a GWT application based on MVP/Event bus.

## Events ##

Even if GWTP tries to reduce the number of events needed for an application, you will still need to create a certain number of them. As I said before, GWTP is based on the GWT handler manager whereas Mvp4g introduces a new way to create events.

This is how you would create an event and add a handler.

<table><tr align='center'><td>Mvp4g</td><td>GWTP</td></tr>
<tr valign='top'><td>
<i>Event bus: creating an event and registering an handler</i>
<pre><code>//this method is defined inside your event bus interface, <br>
//you need one interface for your whole application<br>
@Event(handlers = OnePresenter.class)<br>
void oneEvent(BeanOne one, BeanTwo two);<br>
</code></pre>

<i>Presenter: defining the handling method</i>
<pre><code>public class OnePresenter ... {<br>
<br>
    public void onOneEvent(BeanOne one, BeanTwo two){<br>
      //do sthg<br>
    }<br>
<br>
}<br>
</code></pre>

<i>Firing an event</i>
<pre><code>eventBus.oneEvent(beanOne, beanTwo);<br>
</code></pre>
</td><td>
<i>Creating an event (regular way with a GWT Handler Manager)</i>
<pre><code>public class OneEvent extends<br>
    GwtEvent&lt;OneEvent.OneHandler&gt; {<br>
  public interface OneHandler extends EventHandler {<br>
    void onOneEvent(OneEvent event);<br>
  }<br>
<br>
  private static Type&lt;OneHandler&gt; TYPE = new Type&lt;OneHandler&gt;();<br>
  <br>
  private BeanOne one;<br>
  private BeanTwo two;<br>
  <br>
  public OneEvent(BeanOne one, BeanTwo two){<br>
    this.one = one;<br>
    this.two = two;<br>
  }<br>
  <br>
  @Override<br>
  public Type&lt;ShowMessageHandler&gt; getAssociatedType() {<br>
    return TYPE;<br>
  }<br>
<br>
  @Override<br>
  protected void dispatch(OneHandler handler) {<br>
    handler.onOneEvent(this);<br>
  }    <br>
  <br>
} <br>
</code></pre>
<b>OR</b> <i>Creating an event with the new tool provided by GWTP<br>
(it generates the code above thanks to the following code)</i>
<pre><code>@GenEvent  <br>
public class OneEvent {<br>
 <br>
  @Order(1) BeanOne one;       <br>
  @Order(2) BeanTwo two;<br>
   <br>
}<br>
</code></pre>

<i>Presenter: registering the handler and defining the handling method</i>
<pre><code>public class OnePresenter ... implements OneHandler {<br>
<br>
    public void onOneEvent(BeanOne one, BeanTwo two){<br>
      //do sthg<br>
    }<br>
    <br>
    public void onBind(){<br>
        addRegisteredHandler( OneEvent.Type, this );<br>
    }<br>
<br>
} <br>
</code></pre>
<i>firing an event</i>
<pre><code>eventBus.fire(new OneEvent(bean1, bean2));<br>
</code></pre>
</td></tr></table>

Even if GWTP has introduced a new tool to generate the boilerplate code needed by events, the Mvp4g process is still simpler: **1 line of code & 1 annotation** to create an event and add x handlers compared to a **few annotations, a class and x lines of code for GWTP**. When you have dozens of events and handlers (which is most likely going to happen for any application no matter what framework you use), you can save a lot of lines of codes with Mvp4g.

I think another main advantage of Mvp4g compared to GWTP is that you can easily control your events and their flow. The manner in which Mvp4g implements events allows you to have all your events listed in one place. You can then easily see your event handlers:
```
@Event(handlers = {PresenterOne.class, PresenterTwo.class})
void oneEvent(BeanOne one, BeanTwo two);
```
For example, here, right away, you can see that oneEvent is handled by PresenterOne & PresenterTwo.

Another great advantage is that just by looking at one interface, you can have a good idea of how the application works.

With GWTP (or any framework based on GWT HandlerManager), you have to go through several classes to retrieve this information.

Here is a list of other available features related to the event bus:
<table width='100%'><tr><td align='center'>
<table><thead><th><b>Mvp4g</b></th><th> <b>GWTP</b></th></thead><tbody>
<tr><td>Event Filtering</td><td>Not available</td></tr>
<tr><td>Event Logs  </td><td>Not available</td></tr>
<tr><td>Activation/deactivation of handlers</td><td>can be done but with more lines of codes</td></tr>
</td></tr></table></tbody></table>

## Creating a presenter ##

<table><tr align='center'><td><b>Mvp4g</b></td><td><b>GWTP</b></td></tr>
<tr valign='top'><td>
<i>Presenter</i>
<pre><code>@Presenter(view=OneView)<br>
public class OnePresenter extends LazyPresenter&lt;OneEventBus, IOneView){...}<br>
</code></pre>
</td><td>
<i>Presenter</i>
<pre><code>public class OnePresenter extends PresenterImpl&lt;IOneView, OnePresenter.MyProxy&gt; {   <br>
    @ProxyStandard     <br>
    public interface MyProxy extends Proxy&lt;MainPagePresenter&gt; {} <br>
    ...<br>
}<br>
</code></pre>
<i>GIN module</i>
<pre><code>bindPresenter(OnePresenter.class, IOneView.class, OneView.class, OnePresenter.MyProxy.class);         <br>
</code></pre>
<i>GIN injector</i>
<pre><code>Provider&lt;OnePresenter&gt; getOnePresenter(); <br>
</code></pre>
</td></tr></table>
As you can see the process is easier with Mvp4g since you can create your presenter and inject the view thanks to one annotation. Mvp4g also uses GIN to generate the presenters/views, but it hides its complexity and you will have to manipulate GIN classes only if you need extra configuration.

Mvp4g & GWTP both manage non-singleton presenters.

GWTP offers a system to protect presenters so that developers can control user access (with Mvp4g, you would use event filtering to prevent your presenters from being called).

## Nested Presenters (or how to display presenter's view inside other presenters' view) ##

GWTP integrates a tool to do this thanks to one event provided by GWTP.

On the contrary, Mvp4g doesn't provide any specific tool and relies on events. However, if we compare the 2 frameworks, the amount of code needed is pretty much the same.

<table><tr align='center'><td><b>Mvp4g</b></td><td><b>GWTP</b></td></tr>
<tr valign='top'><td>
<i>Event bus</i>
<pre><code>@Event(handlers = ParentPresenter.class)<br>
void setView(Widget childView);<br>
</code></pre>
<i>Parent presenter</i>
<pre><code>public void onSetView(Widget childView){<br>
  parentView.setView(childView);<br>
}<br>
</code></pre>
<i>Parent view</i>
<pre><code>public void setView(Widget childView){<br>
  //place view<br>
}<br>
</code></pre>
</td><td>
<i>Parent Presenter</i>
<pre><code>public static final Type&lt;RevealContentHandler&lt;?&gt;&gt; TYPE_ChildView = new Type&lt;RevealContentHandler&lt;?&gt;&gt;();<br>
</code></pre>
<i>Parent View</i>
<pre><code>@Override <br>
public void setContent(Object slot, Widget content) { <br>
  if (slot == ParentPresenter.TYPE_ChildView) { <br>
     setChildView(content); <br>
  } else { <br>
     super.setContent(slot, content); <br>
  } <br>
}<br>
<br>
public void setChildView(Widget content){<br>
  ...<br>
}<br>
</code></pre>
</td></tr></table>

GWTP also provides a particular type of nested presenter: Tabbed presenters.


## Place Service ##

The interpretation of the place service is also different: Mvp4g links a place to an event whereas GWTP links a place to a presenter.

Linking a place to an event is more flexible because you can have a many-to-many relationship between tokens and presenters (since a token, transformed to an event, can be handled by several presenters and a presenter can handle several events).

With the GWTP solution, you have a one-to-one relationship. With the one-to-one relationship, these problems can't be easily resolved :
  * for a particular token, you want two actions: for example, you want to display a presenter's view and select a menu.
  * you want a presenter to react to several tokens: for example, you have a menu bar that needs to display different menus according to the token.
GWTP plans to remove this limitation to allow many-to-many relationships between token and presenter (see GWTP [issue 78](https://code.google.com/p/mvp4g/issues/detail?id=78)).

Also linking a place to an event is something that I have found to be really powerful. Thanks to this technic, you can fire events by url. It is especially useful when you want another application to interact with your GWT project (for example, you can easily add a confirmation link inside an email).

They are 2 things I like about GWTP place service that I'd like to add to Mvp4g:
  * when you switch place (ie token), you can display a confirm message (useful incase you're in a create page and you want user's confirmation to leave the page). Right now, you can only use the browser confirm and it would be nice to be able to use a custom one.
  * you can easily ask a presenter to reveal itself if you don't need to pass any object to it :
```
placeManager.revealPlace( new PlaceRequest("desiredNameToken") ); 
```
If you do have to pass an object, then you will have no other choice than to create an event (which is easier with Mvp4g).


Here is the code needed in case you want to create a place with one parameter:

<table><tr align='center'><td><b>Mvp4g</b></td><td><b>GWTP</b></td></tr>
<tr valign='top'><td>
<i>History converter to convert the event to/from a token</i>
<pre><code>public class OneHistoryConverter ... {<br>
<br>
  public String onOneEvent(String id){<br>
      return "id=" + id;<br>
  }<br>
  <br>
  public void convertFromToken( String eventType, String param, OneEventBus eventBus ){<br>
      long id = Long.parse(param.split("=")[1]);<br>
      eventBus.oneEvent(id);<br>
  }<br>
  <br>
}<br>
</code></pre>
<i>Event bus: associating the history converter to the event</i>
<pre><code>@Event(..., historyConverter=OneHistoryConverter.class, historyName="oneHistoryEvent")<br>
void oneEvent(long id);  <br>
</code></pre>

</td><td>
<i>Presenter: associating a token to the presenter, defining method to handle the tokens</i>
<pre><code>public class OnePresenter ... {<br>
<br>
  private long id;<br>
  <br>
  @ProxyCodeSplit<br>
  @NameToken("oneHistoryEvent")<br>
  public interface MyProxy extends ProxyPlace&lt;AboutUsPresenter&gt; {...}<br>
  <br>
  @Override   <br>
  public void prepareFromRequest(PlaceRequest placeRequest) {<br>
       super.prepareFromRequest(placeRequest);      <br>
       long id = placeRequest.getParameter("id", null);     <br>
  }            <br>
<br>
}<br>
</code></pre>
</td></tr></table>

Both frameworks have basically the same amount of code. However, I think the Mvp4g place service is easier to understand & cleaner since you don't mix your application logic and your token convertion process in the same class.

## Code splitting ##
Both frameworks manage code splitting, however once again their approach is different:
  * Mvp4g forces you to divide your application into sub-modules. Each sub-module will have its own event bus and its own presenters/views. Sub-modules can communicate with each other thanks to events.
  * GWTP is more flexible and lets you split any presenters or group of presenters.

<table><tr align='center'><td><b>Mvp4g</b></td><td><b>GWTP</b></td></tr>
<tr valign='top'><td>
<i>Mvp4g Child Module</i>
<pre><code>public interface OneSubModule extends Mvp4gModule {...}<br>
</code></pre>
<i>Child Module Event bus</i>
<pre><code>@Events(..., module = OneSubModule.class)<br>
public interface OneSubEventBus extends EventBus {<br>
<br>
    //settings events to forward to parent if needed<br>
    @Event(forwardToParent = true)<br>
    public void oneParentEvent(...)<br>
<br>
    @Event(forwardToParent = true)<br>
    public void anotherParentEvent(...)<br>
<br>
}<br>
</code></pre>
<i>Parent event bus: add child module</i>
<pre><code>@ChildModules( { @ChildModule(moduleClass = OneSubModule.class) })<br>
@Events(...)<br>
public interface RootEventBus ... { <br>
<br>
    //settings events to forward to child module<br>
    @Event(modulesToLoad = {OneSubModule.class } )<br>
    public void oneChildEvent(...)<br>
<br>
    //settings events to forward to child module<br>
    @Event(modulesToLoad = {OneSubModule.class } )<br>
    public void anotherChildEvent(...)<br>
<br>
}<br>
</code></pre>
</td><td>

<i>To split a single presenter</i>
<pre><code>public class OnePresenter ... {   <br>
<br>
    @ProxyCodeSplit   <br>
    public interface MyProxy extends ProxyPlace&lt;HomePresenter&gt;  {}<br>
    ...<br>
} <br>
</code></pre>

<i>To split a group of presenters</i>
<pre><code>public class OnePresenterBundle extends ProviderBundle {<br>
      //one for each presenter<br>
      public static final int ID_OnePresenter = 0;<br>
      public static final int ID_AnotherPresenter = 1;    <br>
      public static final int BUNDLE_SIZE = 2;        <br>
      @Inject    <br>
      TabbedPresenterBundle(final Provider&lt;OnePresenter&gt; onePresenter, final Provider&lt;AnotherPresenter&gt; anotherPresenter) {<br>
            super(BUNDLE_SIZE);<br>
            providers[ID_OnePresenter] = anotherPresenter;<br>
            providers[ID_AnotherPresenter] = anotherPresenter;    <br>
       }<br>
}<br>
</code></pre>
<pre><code>//config needed for each presenter<br>
public class OnePresenter ... {<br>
<br>
    @ProxyCodeSplitBundle( bundleClass = OnePresenterBundle.class, id = OnePresenterBundle.ID_OnePresenter)<br>
    public interface MyProxy extends Proxy&lt;UserSettingsTabPresenter&gt; { }<br>
    ...<br>
} <br>
</code></pre>
</td></tr></table>

I have to admit code splitting is easier with GWTP and a lot more flexible. It is really easy to add or remove a presenter from a slice. The only thing I would say is that splitting a presenter by itself should be used with caution as you don't want to create slices that are too small.

One thing I like with the Mvp4g Code splitting feature, is that it forces you to:
  * divide your application into sub-modules
  * set the communication between each sub-modules via events

This way you end up with a project divided into smaller pieces that are pretty independent from each other. You can then have different developers work on them.

## Optimization ##

Both frameworks implement a pattern to build presenters only when they need to handle their first event, thanks to lazy loading for Mvp4g or proxies for GWTP. This way, your application runs faster.

## Server Communication ##

Mvp4g doesn't integrate any library to communicate with the server, it lets you choose the one you want. Mvp4g can be used with gwt-dispatch for example.

GWTP integrates and improves gwt-dispatch (http://code.google.com/p/gwt-platform/wiki/ComparisonWithGwtDispatch). You can still use another library if you want to.


## Conclusion ##

I haven't gone over all the features offered by Mvp4g or GWTP but I hope this gives you a better understanding of the 2 frameworks.

I think GWTP is a good framework that allows you to build efficient applications. I still prefer Mvp4g, however, because it allows you to build fast and efficient applications in a much simpler manner (so you need less code) and it's more flexible (since everything relies on events).

The main advantage of Mvp4g over GWTP is definitely the way you can define your event bus: it's easy, fast and less time consuming to maintain. The event bus is the heart of your application so it's important for it to be done well.

Now my opinion may be bias(since I'm the creator of Mvp4g) so I'd like to hear yours...