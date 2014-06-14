'use strict';

/* http://docs.angularjs.org/guide/dev_guide.e2e-testing */

describe('BankApp', function() {

  it('should redirect products.html to products.html#/dashboard', function() {
    browser().navigateTo('products-e2e.html');
    expect(browser().location().url()).toBe('/dashboard');
  });



  describe('Dashboard', function() {

    beforeEach(function() {
      browser().navigateTo('products-e2e.html#/settings');
      input('$storage.config.key').enter('')
      browser().navigateTo('products-e2e.html#/dashboard');
    });

    it('two balances are displayed', function() {
      expect(repeater('tr.balances').count()).toBe(2);
    });

    it('balnces should show Girokonto and Total balnces enetry', function() {
      expect(repeater('tr.balances').column('balance.name_enc')).
          toEqual(["Girokonto",
                   "Total"]);
    });
    


    /*it('should be possible to control phone order via the drop down select box', function() {
      input('query').enter('tablet'); //let's narrow the dataset to make the test assertions shorter

      expect(repeater('.phones li', 'Phone List').column('phone.name')).
          toEqual(["Motorola XOOM\u2122 with Wi-Fi",
                   "MOTOROLA XOOM\u2122"]);

      select('orderProp').option('Alphabetical');

      expect(repeater('.phones li', 'Phone List').column('phone.name')).
          toEqual(["MOTOROLA XOOM\u2122",
                   "Motorola XOOM\u2122 with Wi-Fi"]);
    });


    it('should render phone specific links', function() {
      input('query').enter('nexus');
      element('.phones li a').click();
      expect(browser().location().url()).toBe('/phones/nexus-s');
    });*/
  });


  /*describe('Phone detail view', function() {

    beforeEach(function() {
      browser().navigateTo('../../app/index.html#/phones/nexus-s');
    });


    it('should display nexus-s page', function() {
      expect(binding('phone.name')).toBe('Nexus S');
    });


    it('should display the first phone image as the main phone image', function() {
      expect(element('img.phone').attr('src')).toBe('img/phones/nexus-s.0.jpg');
    });


    it('should swap main image if a thumbnail image is clicked on', function() {
      element('.phone-thumbs li:nth-child(3) img').click();
      expect(element('img.phone').attr('src')).toBe('img/phones/nexus-s.2.jpg');

      element('.phone-thumbs li:nth-child(1) img').click();
      expect(element('img.phone').attr('src')).toBe('img/phones/nexus-s.0.jpg');
    });
  });*/
});
