'use strict';

/* Filter */

angular.module('productFilters', [])
    .filter('checkmark', function () {
        return function (input) {
            return input ? '\u2713' : '\u2718';
        };
    })
    .filter('joinByName', function () {
        return function (input, name) {
            return name + input.map(function (item) {
                return item.name;
            }).join(" ")
        };
    })
    .filter('formatFileSize', function () {

        return function (bytes) {
            var $config = this.defaults = {
                // Byte units following the IEC format
                // http://en.wikipedia.org/wiki/Kilobyte
                units: [
                    {size: 1000000000, suffix: ' GB'},
                    {size: 1000000, suffix: ' MB'},
                    {size: 1000, suffix: ' KB'}
                ]
            };
            if (!angular.isNumber(bytes)) {
                return '';
            }
            var unit = true,
            i = -1;
            while (unit) {
                unit = $config.units[i += 1];
                if (i === $config.units.length - 1 || bytes >= unit.size) {
                    return (bytes / unit.size).toFixed(2) + unit.suffix;
                }
            }
        };
    });
