#instaly coding without browser refresh

[Livereload Browser Extensions](http://feedback.livereload.com/knowledgebase/articles/86242-how-do-i-install-and-use-the-browser-extensions-)

[Setup livereload](https://coderwall.com/p/-uvdya)

[Livereload Github](https://github.com/guard/guard-livereload)


Install the livereload guard plugin
```bash
gem install guard-livereload
```

Create default guard file
```bash
guard init livereload
```

An guard configuration example for play that watch changes in the app and conf folder and changes of css, js and html file in the public folder. All that changes trigger an browser reload (not a full page reload).
```ruby
# A sample Guardfile
# More info at https://github.com/guard/guard#readme

guard 'livereload' do
  watch(%r{app/.+\.(scala)$})
  watch(%r{public/.+\.(css|js|html)})
  watch(%r{conf/.})
end
```

That start the guard with the configuration above 
```bash
guard start
```
and the output should be as follow
```bash
10:19:57 - INFO - Guard is using NotifySend to send notifications.
10:19:57 - INFO - Guard is using TerminalTitle to send notifications.
10:19:57 - INFO - LiveReload is waiting for a browser to connect.
10:19:57 - INFO - Guard is now watching at '/home/vagrant/workspace/frank/bankapp'
[1] guard(main)> 10:20:11 - INFO - Browser connected.
10:20:26 - INFO - Reloading browser: conf/application-e2e.conf
10:20:53 - INFO - Reloading browser: app/controllers/BalanceController.scala
[1] guard(main)> 
```
If you have activate the livereload extension in your browser you should see
```bash
INFO - Browser connected
```
The Reloading browser messages obtain after you change a file under watching.

The last step is to install a browser extension in your browser of choice see the first link there are an Firefox, Chrome and Opera extension. After the installation activate the livereload extension and you have never do a refresh page again.

##Use Cases

###Development

Go ahead and change your frontend code for example a css defintion and show more or less in realtime how that affects your page design. or change some javascript and look how your code is reload and depends on your app maybe run after reload. This livereload guard actiavte very simple html auto reload feature like the play 2 framework offered for Scala.

###Continous Testing

Lift up the TDD development to the next level by combining this reload feature with [e2e testing](https://github.com/pussinboots/bankapp/blob/master/doc/e2etest.md). Than every change you did on the frontend or backend
will trigger a complete test run of your backend (unit, integration tests) and frontend (unit tests) and the magic comes with the e2e tests that are also performed. And this tests runs can be executed in parallel. Here is the simple setup i used for the local development of that project here.

* continous Play 2 unit testing

start your play console with play command and than
```bash
~test
```
that run all tests under the test folder if you did some changes to the scala/java files of your code base. This test run could take a long time depens how big is your test suite. So to get a fast feedback of your changes you could start a second play console
```bash
~test-only unit.*
```
with the test-only command you can run only one test or all tests that match a regular expression like you see above. This run all tests in the unit package and all sub packages. If you working on a specific part of your application than to get a faster feedback you could run only the relevant tests to get a fast feedback if you changes broke some thing and so on. But remember you have on play console that runs the relevant tests for fast feedback and a second console that run all tests. So you get a fast and complete test report.

So that was the setup for the backend tests.

* continous e2e tests with karma

For a detail description how you setup [e2e testing with karma](https://github.com/pussinboots/bankapp/blob/master/doc/e2etest.md) for angularjs frontend and play 2 backend. The topic here is how to start the continouse testing process. 

First start the play backend.
```bash
play -Dconfig.file=conf/application-e2e.conf run
```
The application-e2e.conf configuration file is explained in the e2e testing documentation. Now you should have a running server that can be accessed via http://localhost:9000. The play run command take care of autoreloading and compiling changed scala files.

extraction of the [karma.conf-e2e.js](https://github.com/pussinboots/bankapp/blob/master/karma.conf-e2e.js) file
```javascript
autoWatch: true,
usePolling : true,
```

Second start the karma continouse test running mode.
```bash
./node_modules/.bin/karma start karma.conf-e2e.js
```
The same like above for a explanation of the karma.conf-e2e.js file see the e2e testing documentation. 
Than you should see something like this. And in the rigth corner besides the menu icon you see a circle that is the livereload extension. So activate them and see the magic.
![karma runner](https://raw.githubusercontent.com/pussinboots/bankapp/master/public/images/karma_runner_firefox.png)
Karma starts the firefox with no installed extension, i only found the workaround install the extension after the karma runner is started. Installation is easy follow the link at the top of the document called Livereload Browser Extensions there you find some extension install it and for firefox restart it. So than the extension should be ready. After the activation now you can change javascript files or even scala files and the e2e karma tests will run automaticly for scala files it could take some time because the browser has to wait until play finsihed compiling and relaoding of the changed file.

##Conclusion

It costs me only minutes to setup guard livereload and it make the coding live so easier sometimes i hit the reload button on the browser so often that i think it will break the next time. I'am a beginner in this unit, integration, e2e test continous local stuff but i can feel the power and the productivity increase of course it depends on a good test suite. But than you can instanly refactor you code and see the konsequences. At the moment i use sublime text editor for coding because it is fast and has all features to code quick. So the time i wait for my ide to come back that i could coding again are gone but also that is true full code completion and other really good features of an ide are missing. But with some terminals running like described above and the code search on the internet i have the feeling i am faster than with an ide. Depends also on the project size. This approach here could also work well if you programm with an ide maybe let me know if it also feel good and productive with an ide besides.


