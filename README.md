bankapp
=======
[![Build Status](https://travis-ci.org/pussinboots/bankapp.svg?branch=master)](https://travis-ci.org/pussinboots/bankapp)
[![Coverage Status](https://img.shields.io/coveralls/pussinboots/bankapp.svg)](https://coveralls.io/r/pussinboots/bankapp?branch=master)
[![Heroku](http://heroku-badge.heroku.com/?app=bana)](https://bana.herokuapp.com)

There is also a little leasons learned documentation in the doc folder
* [E2E Test angularjs + Play](https://github.com/pussinboots/bankapp/blob/master/doc/e2etest.md) TODO !!!
* [Multilanguage Project forntend javascript backend scala](https://github.com/pussinboots/bankapp/blob/master/doc/nodejs_play2.md) TODO !!!
* [multiple build pack on heroku](https://github.com/pussinboots/bankapp/blob/master/doc/buildpack.md)
* [Play 2 c3p0 own truststore and keystore](https://github.com/pussinboots/bankapp/blob/master/doc/c3p0.md)
* [IDE Experiences](https://github.com/pussinboots/bankapp/blob/master/doc/ide.md)
* [Git Shortcuts](https://github.com/pussinboots/bankapp/blob/master/doc/git.md)

Little personal bank web application that show my account and stock overview of my 
bank account. The data is fetched from the bank account web site and it is only run local
because of security constraints by passing my bank account login data. 

The project is split in a display  and import bank data module. So the display part can run on the web 
but the import part should only be run localy because of the previous mentioned security constrain.

It is also a proof of concept for client side encryption/decryption with the (angularjs crypto modul)[https://github.com/pussinboots/angularjs-crypto]. The bank account and stock data are
stored on a hosted database like cleardb. But this data are encrypted and will only be decrypted on the 
client side in angularjs so that the encryption/decryption key is not know to the systems where the
server part is running and the data are stored to get. I will be prepare a little live demo in
the next days with random bank data.

##Plans

Make it possible to insert data by ui and api so that anyone can store his or here bank data and use this app.
To get a quick start it use google plus for signin. I hope i get it working for other customer than me and
i plan also to implement a little mobile app with PhoneGap.

There is already a import modul which fetch the data from the berliner sparkassen web site and stored it encrypted in the database
(see)[https://github.com/pussinboots/bankapp/blob/master/app/model/SparkassenApp.scala] this read the login information from the
environment variables and should work for all berliner sparkassen customers (but of course not tested yet). After there is an import API
than it is possible to write for your own bank a importer which fetch the data somewhere for you and use the offered import api
to insert your bank data. But be carefull run the importer only from your local maschine because you should not store your bank login somewhere on the net. 

##Todos
* build a android phonegap app and publish it in google play
* at the moment only for my account there exists bank data
* rest interface to insert encrypted bank data for any account how wants to use the app
* historical graphs that show the development of your money
* forcast of mone development based on historical data
* some fancy features i can imagine now

##Features

There are several use cases in my mind what is possible with that bank data for example you can group your income and outcome by categories to see where you money goes and this stuff. Also a future calculation of your money behavoir could be possible by analyzing the past and get a forecast what your bank account could loke like in 6 Months and so on. 

##Motivation

I want to get a good and quick overview of my bank account and historical development. That's missing in the current applications i guess.

Feel free to contact me for any purpose.

##Screenshots

Here are some mobile fancy screen shots of course with dummy data not my real bank data.

* here you see the requestable pages
 
![menu](https://raw.githubusercontent.com/pussinboots/bankapp/master/public/images/menu.png)

* here you see the raw data from the database and you see all data, except the date, are encrypted. That is how they are stored in the database and if the right key is configured at the settings page (see next image) than the data will be readable

![encrypted](https://raw.githubusercontent.com/pussinboots/bankapp/master/public/images/encrypted.png)

* here your configure the encryption key (has to be the same key that was used to encrypt the data for storing it in the database)

![key](https://raw.githubusercontent.com/pussinboots/bankapp/master/public/images/key.png)

* here the balance data readable all encryption is performed on the client side (on the device that use this app) 

![balances](https://raw.githubusercontent.com/pussinboots/bankapp/master/public/images/balances_mobil.png)

* here the stocks data readable all encryption is performed on the client side (on the device that use this app) 

![stocks](https://raw.githubusercontent.com/pussinboots/bankapp/master/public/images/stocks_mobile.png)

* if you are not loged in than you will see nothing google plus sign is used and only the googleid and your email address is stored in the app database

![logout](https://raw.githubusercontent.com/pussinboots/bankapp/master/public/images/logout.png)
