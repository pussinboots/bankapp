##Use multiple build packs on heroku

I have a bower based frontend and play 2 (scala) based backend project that is deployed to heroku. 
So how was it possible.

* [nodejs build pack](https://github.com/heroku/heroku-buildpack-nodejs.git)
* [scala (play 2) build pack](https://github.com/heroku/heroku-buildpack-scala.git)

But heroku only support to define one build pack per project so bad you can guess. So the (multi build pack)[https://github.com/ddollar/heroku-buildpack-multi.git]
came to rescue you. It is very simple add a file called .buildpacks to the root of your project and set the multi build pack for heroku.

'''bash
heroku config:add BUILDPACK_URL=https://github.com/ddollar/heroku-buildpack-multi.git
'''

Done.
