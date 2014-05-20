'use strict';

/* Services */

angular.module('productServices', ['ngResource'], function ($provide) {

    $provide.factory('Balances', function ($resource) {return $resource('/assets/rest/balances', {}, {
            get: {method: 'GET', isArray: true}
        });
    });

    $provide.factory('Stocks', function ($resource) {return $resource('/assets/rest/stocks', {}, {
            get: {method: 'GET', isArray: true}
        });
    });

    $provide.factory('YqlQuotes', ['$q', '$http', function($q, $http) {
    var yqlQueryUrl = 'http://query.yahooapis.com/v1/public/yql?q=';
    var yqlOptions = '&format=json&callback=JSON_CALLBACK';

    function sanitizeString(str) {
      return str.replace(/[^\w\s.-]+/gi, '').replace(/\s+/g, '+');
    }

    function sanitizeUsDate(str) {
      var res = '0000-00-00';
      var isValidUsDate = false;

      str = str.replace(/[^\d-]+/gi, '');
      isValidUsDate = /\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])/.test(str);

      if (isValidUsDate === true) {
        res = str;
      }
      return res;
    }

    function buildQuery(yqlArray) {
      return yqlQueryUrl + encodeURIComponent(yqlArray.join(' ')) + yqlOptions;
    }

    function executeQuery(queryUrl, deferObj) {
      console.log(queryUrl);
      $http.jsonp(queryUrl)
        .success(function(data) {
          var result;

          if(data && data.hasOwnProperty('query')) {
            result = data.query.results;
            deferObj.resolve(result);
          } else {
            result = 'No data received.';
            console.log(result);
            deferObj.reject(result);
          }

        })
        .error(function(data, status) {
          var result = 'Error while fetching quotes.';
          console.error(result, data, status);
          deferObj.reject(result);
        });
    }

    return {

      getSymbol: function(str) {
        var deferred = $q.defer();

        /***
         *  USE "https://gist.github.com/jotbe/ee2bd20184b936a5a731/raw" AS symbol;
         *  SELECT * FROM symbol
         *  WHERE symbol = "GFT.DE";
         *
         *  REST-Query:
         *  http://query.yahooapis.com/v1/public/yql?q=
         *  USE%20%22https%3A%2F%2Fgist.github.com%2Fjotbe%2Fee2bd20184b936a5a731%2Fraw%22%20AS%20symbol%3B%20
         *  SELECT%20*%20FROM%20symbol%20WHERE%20symbol%20%3D%20%22post%22
         *  &format=json&callback=JSON_CALLBACK
         */
        var yqlQuery = [
          'USE "https://gist.github.com/jotbe/ee2bd20184b936a5a731/raw" AS symbol;',
          'SELECT * FROM symbol WHERE symbol = "' + sanitizeString(str) + '"'
        ];

        var query = buildQuery(yqlQuery);
        // console.log('Executing Query:', query);
        executeQuery(query, deferred);

        return deferred.promise;
      },

      getQuotes: function(syms) {
        if (typeof(syms) === 'string') {
          syms = [syms];
        }

        syms = syms.map(sanitizeString);

        var symbols = syms.join('","');
        var deferred = $q.defer();

        /***
         *  USE "https://gist.github.com/jotbe/e3117bcdcbf1f3cc6c89/raw" as quote;
         *  SELECT * FROM quote
         *  WHERE symbol = "GFT.DE";
         *
         *  REST-Query:
         *  http://query.yahooapis.com/v1/public/yql?q=
         *  USE%20%22https%3A%2F%2Fgist.github.com%2Fjotbe%2Fe3117bcdcbf1f3cc6c89%2Fraw%22%20AS%20quote%3B%20
         *  SELECT%20*%20from%20quote%20WHERE%20symbol%20IN%20(%22GFT.DE%22)
         *  &format=json&callback=JSON_CALLBACK
         */
        var yqlQuery = [
          'USE "https://gist.github.com/jotbe/e3117bcdcbf1f3cc6c89/raw" as quote;',
          'SELECT * FROM quote',
          'WHERE symbol IN ("' + symbols + '")'
        ];

        var query = buildQuery(yqlQuery);
        executeQuery(query, deferred);

        return deferred.promise;
      },

      getHistoricalQuotes: function(sym, fromDate, toDate) {
        var deferred = $q.defer();

        /***
         *  USE 'https://gist.github.com/jotbe/3f35ceb0f3496c3e2869/raw' as stockhist;
         *  SELECT * FROM stockhist
         *  WHERE symbol = "GFT.DE" AND startDate = "2013-01-01" AND endDate = "2013-07-31"
         *
         *  REST-Query (remove line breaks):
         *  http://query.yahooapis.com/v1/public/yql?q=
         *  USE%20'https%3A%2F%2Fgist.github.com%2Fjotbe%2F3f35ceb0f3496c3e2869%2Fraw'%20as%20stockhist%3B%20
         *  SELECT%20*%20from%20stockhist%20WHERE%20symbol%20%3D%20%22GFT.DE%22%20AND%20
         *  startDate%20%3D%20%222013-01-01%22%20AND%20endDate%20%3D%20%222013-07-31%22
         *  &format=json&callback=JSON_CALLBACK
         */
        var yqlQuery = [
          'USE "https://gist.github.com/jotbe/3f35ceb0f3496c3e2869/raw" AS stockhist;',
          'SELECT * FROM stockhist',
          'WHERE symbol = "' + sanitizeString(sym) + '"',
          'AND startDate = "' + sanitizeUsDate(fromDate) + '"',
          'AND endDate = "' + sanitizeUsDate(toDate) + '"'
        ];

        var query = buildQuery(yqlQuery);
        executeQuery(query, deferred);

        return deferred.promise;
      }
    };
  }]);
});
