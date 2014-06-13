bankapp
=======
[![Build Status](https://travis-ci.org/pussinboots/bankapp.svg?branch=master)](https://travis-ci.org/pussinboots/bankapp)
[![Coverage Status](https://img.shields.io/coveralls/pussinboots/bankapp.svg)](https://coveralls.io/r/pussinboots/bankapp?branch=master)
[![Heroku](http://heroku-badge.heroku.com/?app=bana)](https://bana.herokuapp.com)


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

##Features

There are several use cases in my mind what is possible with that bank data for example you can group your income and outcome by categories to see where you money goes and this stuff. Also a future calculation of your money behavoir could be possible by analyzing the past and get a forecast what your bank account could loke like in 6 Months and so on. 

##Motivation

I want to get a good and quick overview of my bank account and historical development. That's missing in the current applications i guess.

Feel free to contact me for any purpose.
