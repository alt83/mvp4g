<b>MVP4g has migrated to GitHub. See <a href='https://github.com/FrankHossfeld/mvp4g'>https://github.com/FrankHossfeld/mvp4g</a> instead.</b>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
GWT is a very powerful framework that allows you to build efficient applications, especially if you follow the best practices described by Ray Ryan at Google IO 2009:<br>
<ul><li>Event Bus<br>
</li><li>Dependency Injection<br>
</li><li>Model View Presenter<br>
</li><li>Place Service<br>
(see <a href='http://code.google.com/events/io/2009/sessions/GoogleWebToolkitBestPractices.html'>http://code.google.com/events/io/2009/sessions/GoogleWebToolkitBestPractices.html</a> for the video or <a href='http://extgwt-mvp4g-gae.blogspot.com/2009/10/gwt-app-architecture-best-practices.html'>http://extgwt-mvp4g-gae.blogspot.com/2009/10/gwt-app-architecture-best-practices.html</a> for the text, thanks to Araik Minosian.)</li></ul>

However, following these best practices is not always easy and you can end up with a project with a lot of boilerplate code that is hard to manage.<br>
<br>
That's why Mvp4g offers a solution to following these best practices using simple mechanisms that only need a few lines of code and a few annotations.<br>
<br>
This is all you need to create an event bus with four events:<br>
<pre><code>@Events(startPresenter = CompanyListPresenter.class, module = CompanyModule.class) <br>
public interface CompanyEventBus extends EventBus {          <br>
	@Event( handlers = CompanyEditPresenter.class )        <br>
	public void goToEdit( CompanyBean company );          <br>
	<br>
	@Event( handlers = CompanyDisplayPresenter.class )         <br>
	public void goToDisplay( CompanyBean company );          <br>
	<br>
	@Event( handlers = { CompanyListPresenter.class, CompanyDisplayPresenter.class } )         <br>
	public void companyCreated( CompanyBean newBean );          <br>
	<br>
	@Event( handlers = CompanyListPresenter.class )         <br>
	public void companyDeleted( CompanyBean newBean ); <br>
}<br>
</code></pre>


Eventbus:<br>
<ul><li>create an event bus using a few annotations and one centralized interface where you can easily manage your events<br>
</li><li>control your event flow thanks to event filtering, event logs, event broadcast, passive event<br>
</li><li>have the same control of user's navigation as the GWT Activities/Place architecture thanks to Navigation Event</li></ul>

MVP:<br>
<ul><li>create a presenter and inject a view with one annotation<br>
</li><li>inject anything you want to your presenters/views thanks to GIN<br>
</li><li>support for multiple instances of the same presenter<br>
</li><li>easily implement the Reverse MVP (or View Delegate) pattern thanks to Reverse View feature<br>
</li><li>easily control your presenter thanks to onBeforeEvent, onLoad and onUnload methods (thanks to the Cycle Presenter feature)</li></ul>

History Management/Place Service:<br>
<ul><li>convert any event to history token thanks to simple history converters<br>
</li><li>support for crawlable urls<br>
</li><li>easily customize your place service<br>
</li><li>support for hyperlink token</li></ul>

Not only does Mvp4g help you follow the best practices, it also provides mechanisms to build fast applications:<br>
<ul><li>support for GWT code splitting feature: easily divide your applications into smaller modules thanks to Multi-Modules feature or (<b>NEW</b>) split one or a few presenters thanks to Splitter.<br>
</li><li>support for lazy loading: build your presenters/views only when you need them. Useless presenters/views are also automatically removed.<br>
</li><li>(<b>NEW</b>) easily develop your application for multiple application. Reuse most of your code for Android or Iphone/Ipad devices and just switch your GIN configuration.</li></ul>

To understand how the framework works, you can look at the documentation, <a href='http://code.google.com/p/mvp4g/wiki/TutorialsExamples#Tutorial'>the tutorials</a> or <a href='http://code.google.com/p/mvp4g/wiki/TutorialsExamples#Examples'>the examples</a>.<br>
<br>
Mvp4g has been successfully used on several commercial projects, <a href='http://code.google.com/p/mvp4g/wiki/TutorialsExamples#Projects'>take a look at a few of them</a>. You can also read and post feedback on <a href='http://gwtgallery.appspot.com/about_app?app_id=485001'>the official GWT App Galery</a>, <a href='http://www.gwtmarketplace.com/#mvp4g'>GWT marketplace</a> or <a href='http://groups.google.com/group/mvp4g/browse_thread/thread/86acc64c5c279af'>Mvp4g forum</a>.<br>
<br>
Any comments or ideas to improve the framework are more than welcome. If you want to help us improve it and contribute to this project, send me an email.<br>
<br>
To ensure quality, library code is covered by JUnit tests (<a href='http://mvp4g.googlecode.com/svn/tags/mvp4g-1.2.0/other/coverage/10_09_10_coverage-summary.png'>See coverage summary result</a> or <a href='http://mvp4g.googlecode.com/svn/tags/mvp4g-1.2.0/other/coverage/10_09_10_coverage.html'>See full covery result</a>)<br>
<br>
<h2>Start using Mvp4g</h2>

To start, just download the latest version of mvp4g from <a href='http://code.google.com/p/mvp4g/wiki/Downloads'>here</a>.