package org.stock.manager

import org.specs2.mutable.Specification
import org.stock.manager.test.Betamax
import co.freeside.betamax.{MatchRule, TapeMode}
import java.util.Comparator
import co.freeside.betamax.message.Request

class SparkassenAppSpec extends Specification {
  sys.props.+=("com.ning.http.client.AsyncHttpClientConfig.useProxyProperties" -> "true")
  //activate betamax proxy for dispatch
  sequential
  isolated //to have seperated hsql databases

  "The SparkassenApp" should {
//    "login into the sparkassen portal" in Betamax(tape="sparkassenhome", mode=Some(TapeMode.WRITE_ONLY), list= Seq(MatchRule.method, MatchRule.uri, MatchRule.query, MatchRule.fragment, MatchRule.headers)) {
//      SparkassenApp.login()
//      1 must beEqualTo(1)
//    }
    "parse account data" in {
      val html = """
        |<?xml version="1.0" encoding="utf-8"?>
        |<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        |<html xmlns="http://www.w3.org/1999/xhtml" lang="de">
        |<head>
        |	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
        |<!--
        |	<base href="http://banking.berliner-sparkasse.de:80/portal/">
        |-->
        |	<meta http-equiv="Content-style-type" content="text/css" />
        |		<script type="text/javascript" src="/ifdata/10050000/IPSTANDARD/4/content/www/js/std.js"></script>
        |	<script type="text/javascript" src="/ifdata/10050000/IPSTANDARD/4/content/www/js/fi/jquery.min.js"></script>
        |	<script type="text/javascript" src="/ifdata/10050000/IPSTANDARD/4/content/www/js/fi/jquery.ui.min.js"></script>
        |	<script type="text/javascript" src="/ifdata/10050000/IPSTANDARD/4/content/www/js/fi/jquery.easing.min.js"></script>
        |
        |	 <script type="text/javascript"><!--
        |    function helpMe(Adresse, Titel) {
        |  		var myHelp = window.open(Adresse, Titel, 'width=450,height=300,resizable=yes,status=no,scrollbars=yes');
        |  		myHelp.focus();
        |	}
        |	function helpMe2(Adresse, Titel) {
        |  		var myHelp = window.open(Adresse, Titel, 'status=no,scrollbars=yes');
        |  		myHelp.focus();
        |	}
        | //--></script>
        |	 <style type="text/css"><!--
        |             @import url("/ifdata/10050000/IPSTANDARD/4/content/www/css/if5_raster.css");
        |                 @import url("/ifdata/10050000/IPSTANDARD/4/content/www/css/if5_container.css");
        |                 @import url("/ifdata/10050000/IPSTANDARD/4/content/www/css/if5_banking.css");
        |                     --></style>
        |
        |                             <!--[if IE 6]>
        |        <link rel="stylesheet" href="/ifdata/10050000/IPSTANDARD/4/content/www/css/if5_ie6.css" type="text/css" media="all" />
        |     <![endif]-->
        |               <!--[if IE 7]>
        |         <link rel="stylesheet" href="/ifdata/10050000/IPSTANDARD/4/content/www/css/if5_ie7.css" type="text/css" media="all" />
        |     <![endif]-->
        |                      <link rel="stylesheet" media="print" href="/ifdata/10050000/IPSTANDARD/4/content/www/css/if5_druck.css" type="text/css" />
        |              <link rel="stylesheet" href="/css/opttan4_0_1.css" type="text/css" />
        |
        |			<link rel="shortcut icon" type="image/x-icon" href="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/favicon.ico" />
        |        <link rel="icon" type="image/x-icon" href="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/favicon.ico" />
        |	<title>Berliner Sparkasse (10050000) - Finanzstatus</title>
        |	<meta name="keywords" content="Banking,Finanzstatus" />
        |	<meta name="description" content="Die Internet-Filiale der Berliner Sparkasse: Online-Banking, Rente, Aktien, Baufinanzierung, ImmobilienangeboteDie Internet-Filiale der Berliner Sparkasse: Online-Banking, Rente, Aktien, Baufinanzierung, Immobilienangebote" />
        |			  	<META HTTP-EQUIV="Refresh" CONTENT="720; URL=/portal/portal/_o:c1ac55412183edd7/LogoutTimeout" />
        | </head>
        |<body class=   "if5"
        |    onload="">
        |	   <script language="javascript" type="text/javascript">
        |  	 	try{ifPage = new IfPage({showFontSizeControl:true,editmode:false,istx:true, pzurl:'https://www.berliner-sparkasse.de/',isElexir:false}) }catch(e){}
        |  </script>
        |
        |
        |<div id="js_VelocityOneColumn_" class="if5_outer">
        |
        |
        |
        |
        |  	<div id="column_VelocityOneColumn_0"
        |	     class="if5_inner">
        |
        |
        |
        |		    			    			    	<div class="if5_header">
        |   	<!--  create Href aus &uuml;bergebenen Strings  --><div class="if5_logo_spk b1"><a href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkY=" target="_top"><img alt="Berliner Sparkasse - Privatkunden" class="logo" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/logo_spk/if5_spk_logo.gif" title="Berliner Sparkasse - Privatkunden" /></a></div>
        |
        |
        |		    			    			    	    <div class="if5_gsw b5" style="background: url(/ifdata/10050000/IPSTANDARD/4/content/www/pool/contentelements/gsw/Weihnachtskredit_2013_FCIZep/op_gen.jpg) no-repeat scroll right top;"><div class="gsw_text"><h2 class="gsw_h1"><a title="Informieren sie sich jetzt zum S-Privatkredit" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9rcmVkaXRlX2xlYXNpbmcvcHJpdmF0a3JlZGl0X3NrcC92b3J0ZWlsZS9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkZrcmVkaXRlX2xlYXNpbmclMkZwcml2YXRrcmVkaXRfc2twJTJGdm9ydGVpbGUlMkYmYW1wO2N0cD04YjExZTgwOGM2YTdmOWNjZGVmODk0NTNhYzJlMzUyNw=="   target="_top">Heimkino, Reise, neue K&uuml;che:<br />Der S-Privatkredit</a></h2><div class="gsw_weiter_link"><p class="l2 weiter_linktext"><a title="Informieren sie sich jetzt zum S-Privatkredit" class="c1" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9rcmVkaXRlX2xlYXNpbmcvcHJpdmF0a3JlZGl0X3NrcC92b3J0ZWlsZS9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkZrcmVkaXRlX2xlYXNpbmclMkZwcml2YXRrcmVkaXRfc2twJTJGdm9ydGVpbGUlMkYmYW1wO2N0cD04YjExZTgwOGM2YTdmOWNjZGVmODk0NTNhYzJlMzUyNw=="   target="_top">Mehr Infos</a></p></div></div></div>
        |
        |</div>
        |
        |
        |		    			    			    		<div class="if5_metanavi">
        |    	<div class="metanavi_blz"><p class="blz">BLZ: 10050000</p></div>
        |
        |
        |
        |		    			    			    	    <div class="metanavi_navi"><ul class="metanavi_list"><li class="metanavi_listitem"><a title="Über uns" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9paHJlX3NwYXJrYXNzZS9pbmRleC5waHA/bj0lMkZtb2R1bGUlMkZpaHJlX3NwYXJrYXNzZSUyRiZhbXA7Y3RwPWViNGZjM2FiMWE5MTcyMjE4YzIyYWU2MTU3NTc4OTYy"   target="_top">&Uuml;ber uns</a></li><li class="metanavi_listitem"><a title="Standorte der Berliner Sparkasse" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9paHJlX3NwYXJrYXNzZS9zdGFuZG9ydGUtZGVyLWJlcmxpbmVyLXNwYXJrYXNzZS9kZXRhaWxzL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRmlocmVfc3Bhcmthc3NlJTJGc3RhbmRvcnRlLWRlci1iZXJsaW5lci1zcGFya2Fzc2UlMkZkZXRhaWxzJTJGJmFtcDtjdHA9MDZmYmUwYmFlZmVkZTRhNmZmZWM0MmRhNGI5OWIzOTQ="   target="_top">Standorte</a></li><li class="metanavi_listitem"><a title="Kontakt" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9rb250YWt0L2tvbnRha3R3ZWljaGUvaW5kZXgucGhwP249JTJGbW9kdWxlJTJGa29udGFrdCUyRktvbnRha3R3ZWljaGUlMkYmYW1wO2N0cD02M2FiYmJmZDI2OTE5NTA2MWE2NTNjMGZiMDEzZTVhMg=="   target="_top">Kontakt</a></li><li class="metanavi_listitem"><a title="Hilfe" href="http://s.de/an5"   target="_blank">Hilfe</a></li><li class="metanavi_listitem"><a title="Karriere" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2thcnJpZXJlL2luZGV4LnBocD9uPSUyRmthcnJpZXJlJTJGJmFtcDtjdHA9N2NjYmI1MTFiNTM4YTQyYjI5YWZiZDA0ODY2N2E0OTA="   target="_top">Karriere</a></li><li class="metanavi_listitem"><a title="Shop" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9iYW5raW5nL3NwYXJrYXNzZW5zaG9wL2ltLXNwYXJrYXNzZW5zaG9wLWZpbmRlbi1zaWUvaW5kZXgucGhwP249JTJGcHJpdmF0a3VuZGVuJTJGYmFua2luZyUyRnNwYXJrYXNzZW5zaG9wJTJGaW0tc3Bhcmthc3NlbnNob3AtZmluZGVuLXNpZSUyRiZhbXA7Y3RwPTYyMTNhMDU3YWYyN2JjZmQ4MGUzOTdhZjhmOGMwNzBm"   target="_top">Shop</a></li><li class="metanavi_listitem"><a title="Videos" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9tZWRpYWNlbnRlci91ZWJlcnNpY2h0L2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRm1lZGlhY2VudGVyJTJGdWViZXJzaWNodCUyRiZhbXA7Y3RwPWY3YjcxNDE0MDQ2OGZmMGQ5YWRlMWVmOGZiZmVjODAz"   target="_top">Videos</a></li></ul><div class="fontsize" id="metanaviloggedinfontsize" style="display:none"><ul style="background:no-repeat;"><li _size="62.5" _stat="statnormal.png" title="Schrift normal"><img alt="Schrift normal" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/fs0.png" /></li><li _size="68.8" _stat="statgroesser.png" title="Schrift größer"><img alt="Schrift größer" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/fs1.png" /></li><li _size="75" _stat="statgross.png" title="Schrift groß"><img alt="Schrift groß" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/fs2.png" /></li></ul></div><script type="text/javascript" language="JavaScript" src="/ifdata/10050000/IPSTANDARD/4/content/www/js/fontsize.js"><!-- --></script><script language="JavaScript" type="text/javascript">try {new FontSizeControl('metanaviloggedinfontsize',{mode:3});} catch(e){}</script></div>
        |
        |
        |
        |		    			    			    	<div class="metanavi_suche"><div class="suchergebnis" id="suchergebnis"></div><form accept-charset="UTF-8" action="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdWNoZS9pbmRleC5waHA=" class="suche" id="suchformular" method="post" target="_top"><input id="suchfeld" type="text" name="words" value="Suchbegriff" size="10" maxlength="50" class="suche_feld is" onmouseover="glSearch(this)" onblur="testField(this, this.firstValue)" onfocus="testField(this, this.firstValue)" /><input type="hidden" name="dummy" value="&#9760;"/>
        |							<input type="image" name="suchbutton" value="Suchen" alt="Suchen" title="Suchen" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/metanavi_weiter_button.gif" class="suche_button c0 l3" onmouseover="glSearch(document.forms.suchformular.suchfeld)"/>
        |							<script id="suche_script" src="/ifdata/10050000/IPSTANDARD/4/content/www/js/suche.js" type="text/javascript"></script></form></div>
        |
        |</div>
        |
        |
        |
        |
        |
        |		    			    			    	<div class="if5_nav b4">
        |    	<span style='display:none;'>ausgew&auml;hlt: Online-Banking: Finanzstatus</span><ul class='nav0wrap'><li class='nav0item open active withsub'><h3><a target='_top' title='Online-Banking' href='/portal/portal/_o:c1ac55412183edd7/?p=p.finanzstatus&amp;n=%2Fonlinebanking%2Ffinanzstatus%2F'>Online-Banking</a></h3><form action='/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19UcmVlTmF2aWdhdGlvblBvcnRsZXR8Zg__/_o:c1ac55412183edd7/?OFAaOOvVtzXcsQMQ.x=1' class='tx_anmelden' method='post' accept-charset='UTF-8' autocomplete='off' onsubmit="javascript:if(this.id == null || this.id =='') {this.id='1';return true;} else {window.alert('Ihre Daten wurden bereits abgesendet!');return false;};"><fieldset><p> Frank Ittermann</p><input type='image' title='Sicher abmelden' alt='Abmelden' name='anmeldebutton' src='/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_abmelden.png'/></fieldset></form><form action='/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19UcmVlTmF2aWdhdGlvblBvcnRsZXR8Zg__/_o:c1ac55412183edd7/' class='tx_anmelden' method='post' accept-charset='UTF-8' autocomplete='off' onsubmit="javascript:if(this.id == null || this.id =='') {this.id='1';return true;} else {window.alert('Ihre Daten wurden bereits abgesendet!');return false;};"><fieldset><input type='hidden' id='direkt' name='direkt' value='true'/><div class='direkteinstieg'><label for='pageid'>direkt zu:</label><select id='pageid' name='p'><option value='/onlinebanking/finanzstatus/' selected='selected'>Finanzstatus</option><option value='/onlinebanking/umsaetze/umsatzabfrage_neu/'>Ums&auml;tze</option><option value='/onlinebanking/banking/ueberweisung/inland/'>&Uuml;berweisung</option><option value='/onlinebanking/banking/komfortueberweisung/'>&Uuml;bertrag</option><option value='/onlinebanking/banking/dauerauftrag_neu/'>Dauerauftrag</option><option value='/onlinebanking/banking/handy_laden/'>Handy laden</option></select><input type='image' title='Direkt zu...' alt='Weiter' name='direktZuButton' src='/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/primlink_navi.gif'/></div></fieldset></form><ul class='nav1wrap'><li class='nav1item active current'><h4><a target='_top' title='Finanzstatus (ausgew&auml;hlt)' href='/portal/portal/_o:c1ac55412183edd7/?p=p.finanzstatus&amp;n=%2Fonlinebanking%2Ffinanzstatus%2F'>Finanzstatus <span style='display:none;'>(ausgew&auml;hlt)</span></a></h4></li><li class='nav1item'><h4><a target='_top' title='Ums&auml;tze' href='/portal/portal/_o:c1ac55412183edd7/?p=p.umsatzabfrage_neuyhaZcp&amp;n=%2Fonlinebanking%2Fumsaetze%2Fumsatzabfrage_neu%2F'>Ums&auml;tze</a></h4></li><li class='nav1item'><h4><a target='_top' title='Kontoauszug' href='/portal/portal/_o:c1ac55412183edd7/?p=p.kontoauszug&amp;n=%2Fonlinebanking%2Fkontoauszug%2F'>Kontoauszug</a></h4></li><li class='nav1item'><h4><a target='_top' title='Banking' href='/portal/portal/_o:c1ac55412183edd7/?p=p.ueberweisung&amp;n=%2Fonlinebanking%2Fbanking%2Fueberweisung%2Finland%2F'>Banking</a></h4></li><li class='nav1item'><h4><a target='_top' title='Brokerage' href='/portal/portal/_o:c1ac55412183edd7/?p=p.depotaufstellung&amp;n=%2Fonlinebanking%2Fbrokerage%2Fdepot_uebersicht%2Fdepotaufstellung%2F'>Brokerage</a></h4></li><li class='nav1item'><h4><a target='_top' title='Kreditkarte' href='/portal/portal/_o:c1ac55412183edd7/?p=p.kreditkartendetails&amp;n=%2Fonlinebanking%2Fkreditkarte%2Fkartendetails%2F'>Kreditkarte</a></h4></li><li class='nav1item'><h4><a target='_top' title='Postfach' href='/portal/portal/_o:c1ac55412183edd7/?p=p.messages&amp;n=%2Fonlinebanking%2Fpostfach%2Fmessages%2F'>Postfach</a></h4></li><li class='nav1item'><h4><a target='_top' title='Offene Auftr&auml;ge' href='/portal/portal/_o:c1ac55412183edd7/?p=p.terminauftraege&amp;n=%2Fonlinebanking%2Foffene_auftraege%2F3ebene%2Fterminauftraege%2F'>Offene Auftr&auml;ge</a></h4></li><li class='nav1item'><h4><a target='_top' title='Sicherheit' href='/portal/portal/_o:c1ac55412183edd7/?p=p.pin_tan_verwaltung&amp;n=%2Fonlinebanking%2Fsicherheit_angemeldet%2Fpin_tan_verwaltung%2F'>Sicherheit</a></h4></li><li class='nav1item '><h4><a target='_top' title='Aktuelles und Service' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9iYW5raW5nL2FrdHVlbGxlLWhpbndlaXNlL2FyY2hpdl8yMDEzL3VlYmVyc2ljaHQvaW5kZXgucGhwP249JTJGb25saW5lYmFua2luZyUyRnNlcnZpY2UlMkZha3R1ZWxsZXMlMkYyMDEzJTJG'>Aktuelles und Service</a></h4></li></ul></li><li class='nav0item close'><h3><a target='_top' title='Online-Produkte' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL29ubGluZS1wcm9kdWt0ZS9ob21lcGFnZS9pbmRleC5waHA/bj0lMkZvbmxpbmUtcHJvZHVrdGUlMkY='>Online-Produkte</a></h3></li><li class='nav0item close'><h3><a target='_top' title='Privatkunden' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkY='>Privatkunden</a></h3></li><li class='nav0item close'><h3><a target='_top' title='Firmenkunden' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2Zpcm1lbmt1bmRlbi9pbmRleC5waHA/bj0lMkZmaXJtZW5rdW5kZW4lMkY='>Firmenkunden</a></h3></li><li class='nav0item close '><h3><a target='_top' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2ZpbmFuemtvbnplcHQvaW5kZXgucGhwP249JTJGc3Bhcmthc3Nlbi1maW5hbnprb256ZXB0JTJG'>Sparkassen-Finanzkonzept</a></h3></li><li class='nav0item open withsub'><h3><a target='_top' title='Spezielle Angebote' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL2p1bmdlLWxldXRlL2luZGV4LnBocD9uPSUyRnppZWxncnVwcGVuJTJGanVuZ2UtbGV1dGUlMkY='>Spezielle Angebote</a></h3><ul class='nav1wrap'><li class='nav1item'><h4><a target='_top' title='Junge Leute' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL2p1bmdlLWxldXRlL2luZGV4LnBocD9uPSUyRnppZWxncnVwcGVuJTJGanVuZ2UtbGV1dGUlMkY='>Junge Leute</a></h4></li><li class='nav1item'><h4><a target='_top' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL3ByaXZhdGUtYmFua2luZy9pbmRleC5waHA/bj0lMkZ6aWVsZ3J1cHBlbiUyRnByaXZhdGUtYmFua2luZyUyRg=='>Private Banking</a></h4></li><li class='nav1item '><h4><a target='_top' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL3MtdmVyZWluL2luZGV4LnBocD9uPSUyRnppZWxncnVwcGVuJTJGcy12ZXJlaW4lMkY='>Vereine</a></h4></li><li class='nav1item '><h4><a target='_blank' title='Mobile Beratung' href='https://service.berliner-sparkasse.de/msgen/mobile-beratung/'>Mobile Beratung</a></h4></li></ul></li></ul>
        |</div>
        |
        |		    			    			    	<div class="if5_content if5_banking">
        | 	  	<div class="if5_content_inner">
        | 	                <div class="contentbereich">
        |        <a title="Contentbereich / Seiteninhalt" name="pagecontent" href="#" accesskey="3"></a>
        |            <div class="if5_seiten">
        |                <div class="if5_white_o">&nbsp;</div>
        |                <div class="if5_rand">
        |                    <div class="if5_verlauf_o">&nbsp;</div>
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |<div class="contentbereich">
        |	<h2 class="contentbereichHeadline">Guten Tag Herr Ittermann,</h2><p class="begruessungText">Sie waren zuletzt angemeldet am <strong>18.11.2013</strong> um <strong>14:00</strong> Uhr.<br class="newline" />Ihre zuletzt verbrauchte <strong><a title="TAN&nbsp;(882676)" href="https://banking.berliner-sparkasse.de/portal/portal/_o:c1ac55412183edd7/?td=baecc0efa088c172&amp;w=PIN_TAN_ADMINISTRATION&amp;r=1868e332b1ccc0f7&amp;mk=21624dd5fc9ea61d" class="pfeilLink">TAN&nbsp;(882676)</a></strong> wurde am <strong>08.11.2013</strong> um <strong>09:01</strong> Uhr benutzt.</p><h3 class="boxHeadline" style="border-top:0px">Hinweise</h3><div class="boxHinweis"><p class="begruessungText">Ihre letzte Sitzung (Session) wurde nicht &uuml;ber die Funktion <strong>&quot;Abmelden&quot;</strong> beendet.<br />Bitte benutzen Sie zu Ihrer eigenen Sicherheit immer diese Funktion. Bitte beachten Sie hierzu unsere <a title="Sicherheitshinweise" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9iYW5raW5nL3NpY2hlcmhlaXQtaW0taW50ZXJuZXQvaW5kZXgucGhw" target="_top" class="pfeilLink">Sicherheitshinweise</a>.</p></div><div class="boxBottom"></div>
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |<!-- Ende  -->
        |</div>
        |                    <div class="if5_verlauf_u">&nbsp;</div>
        |                </div>
        |                <div class="if5_white_u">&nbsp;</div>
        |            </div>
        |        </div>
        |
        |
        |
        |
        |
        |
        |
        |   <div class="contentbereich">
        |    <a title="Contentbereich / Seiteninhalt" name="pagecontent" href="#" accesskey="3"></a>
        |
        |    <h2 class="contentbereichHeadLine">Finanzstatus</h2>
        |
        |
        | <!-- der Rest folgt in "if5portletTransactionStartVol2" -->
        |
        |
        |
        |
        |		    			    			    	 <!-- ohne "if5portletTransactionStartVol1" funktioniert hier nichts -->
        |
        |
        |    <div> <!-- Wrapper -->
        |        <a title="Binnennavigation" name="pagesubnavigation" href="#" accesskey="4"></a>
        |        <h3 class="invisible" style="display:none">Binnennavigation</h3>
        |    </div>
        |    <div class="if5_seiten">
        |         <div class="if5_white_o">&nbsp;</div>
        |         <div class="if5_rand">
        |      <div class="if5_verlauf_o">&nbsp;</div>
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |<div class="contentcontainerMainLayout"><!-- ContentContainerMainLayout auf -->
        |  <SCRIPT LANGUAGE=javascript>
        |<!--
        |function jumpToAnchor(param)
        |{
        |if(param!='null'){
        |    window.location.hash='#'+param;
        |    }
        |}
        |
        |//-->
        |</SCRIPT>
        |
        |
        |    <form id="25b434e2b98180a9" method="post" action="/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19ldm9HdlBvcnRsZXR8YzB8ZDF8ZV9zcGFnZT0xPS9Db250cm9sbGVyLmRv/_o:c1ac55412183edd7/" onsubmit="return teste();">
        |<script type="text/javascript">
        |var submitTime = 0;
        |function teste() {
        |	if (submitTime == 0) {
        |		submitTime = new Date().getTime();
        |	}
        |	field = document.getElementById('GkXKmebWQfUZSQRj');
        |	if ( field.value == '0' ) {
        |		fieldNoCheck = document.getElementById('SwDmQOUfmNOzAmal');
        |		if ( fieldNoCheck.value == '0' ) {
        |			field.value = '1';
        |			return true;
        |		} else {
        |			fieldNoCheck.value = '0';
        |			return true;
        |		}
        |	} else {
        |		if(!(/Firefox[\/\s](\d+\.\d+)/.test(navigator.userAgent)) || new Date().getTime() - submitTime > 1000) {
        |			window.alert('Ihre Daten wurden bereits abgesendet!');
        |			return false;
        |		}
        |	}
        |}
        |
        |function doNoCheck() {
        |	fieldNoCheck = document.getElementById('SwDmQOUfmNOzAmal');
        |	fieldNoCheck.value = '1';
        |}
        |
        |function makeKalenderButtonForElementVisible(elementName) {
        |	try {
        |		var datumsFeld = document.getElementsByName(elementName)[0];
        |		datumsFeld.nextSibling.style.display = "inline";
        |		datumsFeld.nextSibling.style.visibility = "visible";
        | 	}
        |	catch (e) {}
        |}
        |</script>
        |<div style="position:absolute;top:0px;z-index:-50;"><input type="text" name="GkXKmebWQfUZSQRj" value="0" class="invisible" id="GkXKmebWQfUZSQRj" /><input type="text" name="SwDmQOUfmNOzAmal" value="0" class="invisible" id="SwDmQOUfmNOzAmal" /></div>
        |    <a name="topFinanzstatus"></a>
        |        <a name="formular"></a><h3 class="invisible">Berliner Sparkasse (10050000) - Finanzstatus&nbsp;Formular</h3>
        |
        |        <div class="osppformgrund">
        |           <a href="/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19ldm9HdlBvcnRsZXR8YzB8ZDF8ZV9zcGFnZT0xPS9Db250cm9sbGVyLmRv/_o:c1ac55412183edd7/?rMOQGGQwieOrxSoU.x=1" class="submitLink" title="Nach Kontoarten sortieren" alt="Nach Kontoarten sortieren">
        |                <img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_refresh.gif" style="height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfo" title="Nach Kontoarten sortieren" alt="Nach Kontoarten sortieren" />
        |                <span class="submitLinkText" style=''>
        |                    Nach Kontoarten sortieren
        |                </span>
        |           </a>
        |
        |                <a href="/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19ldm9HdlBvcnRsZXR8YzB8ZDF8ZV9zcGFnZT0xPS9Db250cm9sbGVyLmRv/_o:c1ac55412183edd7/?hTYjrYqYPtOGXmqN.x=1" class="submitLink" title="Girokonten-Übersicht" alt="Girokonten-Übersicht">
        |                    <img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" style="height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfo" title="Giro-Detail-&Uuml;bersicht" alt="Giro-Detail-&Uuml;bersicht" />
        |                    <span class="submitLinkText" style=''>
        |                       Girokonten-&Uuml;bersicht
        |                    </span>
        |           </a>
        |
        |
        |        </div>
        |
        |
        |
        |
        |
        |
        |
        |
        |        <table class="tablegrund " cellpadding="0" cellspacing="0" style="BORDER-BOTTOM: 0px none; table-layout:fixed; "><tbody>
        |  <tr class="tableheadline">
        |    <td style="width: 29%;" class="top tabledata">
        |<table cellpadding="0" cellspacing="0"><tr>
        |<td>
        |<input type="image" name="oaamWwCkwPpvKftN" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_redcircle_minus.gif" value="Minimieren" style="cursor: pointer;height: 1.2499999995000002em;width: 1.2499999995000002em;" class="erweiternfinanzstatus" title="Minimieren" alt="Minimieren" /></td>
        |<td>
        |<span class="erweiternbeztable"><a name="863114111"></a>
        |Meine Konten</span>
        |</td>
        |</tr>
        |</table>
        |    </td>
        |    <td style="width: 22%;" align="right" class="" ><a style="text-decoration:none" class="" href="javascript:self.scrollTo(0,0);">
        |<input type="image" name="yZsoUSLlQXkQPdGy" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_up.gif" value="Zum Seitenanfang" onclick="javascript:self.scrollTo(0,0); return false;" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" title="Zum Seitenanfang" alt="Zum Seitenanfang" /> <a style="text-decoration:none" class="osppinfoinhalt" href="javascript:self.scrollTo(0,0);">Seitenanfang</a></a>
        |</td>
        |</tbody></table>
        |<br class="newline"/>&nbsp;
        |<table class="tablegrund tablegrund2 " cellpadding="0" cellspacing="0" style="table-layout: fixed;"><tbody>
        | 	<tr class="tableheadline tablerowoddNew tableheadlinetextNew">
        |    <th class="tableheadlinetext tablerowoddNew tableheadlinetextNew" style="width: 29%;">
        |Kontobezeichnung<br />Kontoverwendung
        |    </th>
        |    <th class="right tableheadlinetext tableheadlinetextNew tablerowoddNew" style="width:22%; ">
        |Kontonummer
        |    </th>
        |    <th style="width: 21%;" class="right tableheadlinetext tableheadlinetextNew tablerowoddNew">
        |Kontostand    </th>
        |    <th style="width: 28%;" class="right tableheadlinetext tableheadlinetextNew tablerowoddNew">
        |Funktionen    </th>
        |  </tr>
        |  <tr class="tablerowodd tablerowevenNew">
        |    <td style="vertical-align: middle;" class="tabledata tabledataNew">Tagesgeldkonto Direkt
        |<em>**</em><br /></td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew" title="IBAN: DE67 1005 0000 1063 3679 44">1063367944
        |</td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew"><span class="habentexttable">21.000,00 EUR</span><br/></td>
        |    <td style="vertical-align: middle;" class="right tabledata tabledataNew">
        |<input type="image" name="plpSBsPgVmjarmyd" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Kontodetails" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Kontodetails" alt="Kontodetails" /><input type="image" name="SHTlhbRixeEBatQX" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_2.gif" value="Umsatzabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Umsatzabfrage" alt="Umsatzabfrage" /><input type="image" name="aJZfazyHXPkCCxtm" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_3.gif" value="Sepa-&Uuml;bertrag" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Sepa-&Uuml;bertrag" alt="Sepa-&Uuml;bertrag" /><input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /><input type="image" name="CFRXRBWkKiXtlnrY" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_aktionen.gif" value="Weitere Funktionen" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Weitere Funktionen" alt="Weitere Funktionen" />    </td>
        |  <tr class="tableroweven tablerowevenNew">
        |    <td style="vertical-align: middle;" class="tabledata tabledataNew">Das Girokonto
        |<em>**</em><br /></td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew" title="IBAN: DE50 1005 0000 4144 0524 57">4144052457
        |</td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew"><span class="habentexttable">4.796,41 EUR</span><br/></td>
        |    <td style="vertical-align: middle;" class="right tabledata tabledataNew">
        |<input type="image" name="tETanTpwpLyqmyeC" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Kontodetails" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Kontodetails" alt="Kontodetails" /><input type="image" name="kKScqQAEQQzbdIhN" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_2.gif" value="Umsatzabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Umsatzabfrage" alt="Umsatzabfrage" /><input type="image" name="oXRDZNbXNTmmoOMI" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_3.gif" value="Sepa-&Uuml;berweisung" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Sepa-&Uuml;berweisung" alt="Sepa-&Uuml;berweisung" /><input type="image" name="BBefZzrzyHWYkMAo" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_5.gif" value="Sepa-Dauerauftrag" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Sepa-Dauerauftrag" alt="Sepa-Dauerauftrag" /><input type="image" name="GMZSPmmfnTyyOUkr" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_aktionen.gif" value="Weitere Funktionen" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Weitere Funktionen" alt="Weitere Funktionen" />    </td>
        |  <tr class="tablerowodd tablerowevenNew">
        |    <td style="vertical-align: middle;" class="tabledata tabledataNew">Tagesgeldkonto Comfort
        |<em>**</em><br />Comfort-Tagesgeld             </td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew" title="IBAN: DE39 1005 0000 6012 6167 14">6012616714
        |</td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew"><span class="habentexttable">988,82 EUR</span><br/></td>
        |    <td style="vertical-align: middle;" class="right tabledata tabledataNew">
        |<input type="image" name="lasmONwgnXIUBNLW" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Kontodetails" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Kontodetails" alt="Kontodetails" /><input type="image" name="fqUOvNUVJNWGDLEU" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_2.gif" value="Umsatzabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Umsatzabfrage" alt="Umsatzabfrage" /><input type="image" name="FzzGfQdaBBDBaqey" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_3.gif" value="Sepa-&Uuml;bertrag" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Sepa-&Uuml;bertrag" alt="Sepa-&Uuml;bertrag" /><input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /><input type="image" name="tjLZLgdJYgHuCdnR" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_aktionen.gif" value="Weitere Funktionen" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Weitere Funktionen" alt="Weitere Funktionen" />    </td>
        |  <tr class="tableroweven tablerowevenNew">
        |    <td style="vertical-align: middle;" class="tabledata tabledataNew">Wertpapierdepot
        |<em>*</em><br /></td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew">6306069320
        |</td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew"><span class="habentexttable">11.124,49 EUR</span><br/></td>
        |    <td style="vertical-align: middle;" class="right tabledata tabledataNew">
        |<input type="image" name="dBOtvPNNHUquEqGR" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Depotaufstellung" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Depotaufstellung" alt="Depotaufstellung" /><input type="image" name="sRwilRCNSdOCEzHx" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_2.gif" value="Orderstatus" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Orderstatus" alt="Orderstatus" /><input type="image" name="WFKQbdDcAjoeyopn" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_plus_rot.gif" value="Wertpapier-Kauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Kauf" alt="Wertpapier-Kauf" /><input type="image" name="tcVIoBZXpErZMcxb" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_minus_rot.gif" value="Wertpapier-Verkauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Verkauf" alt="Wertpapier-Verkauf" /><input type="image" name="jzUXNXtqVfxbBXhP" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_aktionen.gif" value="Weitere Funktionen" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Weitere Funktionen" alt="Weitere Funktionen" />    </td>
        |  <tr class="tablerowodd tablerowevenNew">
        |    <td style="vertical-align: middle;" class="tabledata tabledataNew">VISA Goldkarte
        |<br /></td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew">4532437073997572
        |</td>    <td style="vertical-align: middle;" class="right tabledata tabledataNew"><span class="solltexttable">-195,06 EUR</span><br/></td>
        |    <td style="vertical-align: middle;" class="right tabledata tabledataNew">
        |<input type="image" name="uFBSbJeBxaNnEdTm" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Kreditkartendetails" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Kreditkartendetails" alt="Kreditkartendetails" /><a href="https://banking.berliner-sparkasse.de/if-vz/sso/_o:c1ac55412183edd7/?dop=1&hap=NDUzMjQzNzA3Mzk5NzU3Mg==&sso=ATOS&r=fb0947cae033d0c7" target="_blank"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_legilink.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Neues Fenster:zum Kreditkarten-Banking" alt="Neues Fenster:zum Kreditkarten-Banking" /></a><input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /><input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /><input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" />    </td>
        |  </tr>
        |</tbody></table>
        |<br class="newline"/>&nbsp;
        |<table class="tablegrund tablegrund3 " cellpadding="0" cellspacing="0" style="table-layout: fixed;"><tbody>
        |  <tr class="tablerowodd tablerowoddNew">
        |    <td style="width: 29%;" class="tabledata tabledataNew">&nbsp;</td>
        |    <td style="width: 22%;" class="right tabledata tabledataNew"><b>Gesamtsaldo</b></td>
        |    <td style="width: 21%;" class="right tabledata tabledataNew"><span class="habentexttable">37.714,66 EUR</span><br/></td>
        |    <td style="width: 28%;"class="tabledata tabledataNew">&nbsp;</td>
        |  </tr>
        |</tbody></table>
        |<table class="tablegrund " cellpadding="0" cellspacing="0" style="table-layout:fixed; "><tbody>
        |<tr><td style="padding:0px;"></td></tr></tbody></table>
        |
        |
        |
        |
        |
        |
        |            <div class="osppformgrund">
        |                <div class="osppinfoinhalt">
        |                    <span><em>*</em></span>
        |                    Bewertet mit Vortagesschlusskursen, sofern verf&uuml;gbar.
        |                </div>
        |            </div>
        |
        |
        |            <div class="osppformgrund">
        |                <div class="osppinfoinhalt">
        |                    <span><em>**</em> Kontostand kann Betr&auml;ge mit sp&auml;terer Wertstellung enthalten.</span>
        |
        |                        <a href="https://www.berliner-sparkasse.de/module/glossar/glossar_popup.php?words=Finanzstatus&complete=true" class="pfeilLink" onclick="helpMe(this.href, 'Hilfe1714');return false;" target="_blank" title="Neues Fenster: Hilfe" alt="Neues Fenster: Hilfe">
        |                            <span>Siehe Hilfe</span>
        |                        </a>
        |
        |                </div>
        |            </div>
        |
        |
        |        <a name="pagebuttons" title="Buttonzeile" accesskey="5"></a> <div class="osppbuttonbereich"><h3 class="invisible">Buttonzeile</h3><a href="https://www.berliner-sparkasse.de/module/glossar/glossar_popup.php?words=Finanzstatus&complete=true" target="_blank"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_hilfe_rot.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="osppbuttonlink if5_hilfe if5_hilfe_finanz" title="Neues Fenster:Hilfe Finanzstatus" alt="Neues Fenster:Hilfe Finanzstatus" /></a><a href="https://banking.berliner-sparkasse.de/portal/print/?nc=1&pw=25umSp3kNLChNBL&w=FINANZSTATUS_PRINT&r=3760b3291f8c19b1" target="_blank"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_b_Druckan.png" style="cursor: pointer;height: 2.2499999991000004em;width: 10.499999995800001em;" class="osppbuttonlink" title="Neues Fenster:Druckansicht" alt="Neues Fenster:Druckansicht" /></a><input type="image" name="XwzJDdIzaPScMkVd" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_b_aktualisieren.png" value="Aktualisieren" accesskey="a" style="cursor: pointer;height: 2.2499999991000004em;width: 10.4166666625em;" class="osppbuttonlinklast" title="Aktualisieren" alt="Aktualisieren" /></div>
        |      <body onload="jumpToAnchor('null')">
        |      </body>
        |    <input type="hidden" name="jpkbZvqxEiyHUgmj" value="0921c6b37bf5f0d0" /><input type="hidden" name="QGYEWkrGwQYSQxzA" value="f21c3e39835184ae" /></form>
        |<script type="text/javascript">
        |
        |var notFound = true;
        |var focusControl;
        |for(var i = 0; notFound; i++){
        |   focusControl = document.forms['25b434e2b98180a9'].elements[i];
        |   if(focusControl){
        |       if(focusControl.className=='invisible' || focusControl.type == 'submit' || focusControl.type == 'button' || focusControl.type == 'hidden' || focusControl.disabled){
        |           continue;
        |       }
        |       if(focusControl.type == 'select-one' && focusControl.value !=''){
        |           continue;
        |       }   }
        |   notFound = false;
        |}
        |
        |if (focusControl) {
        |  if (focusControl.type != 'hidden' && !focusControl.disabled) {
        |     focusControl.focus();
        |  }
        |}
        |</script>
        |
        |</div><!-- ContentContainerMainLayout zu -->
        |
        |        <div class="if5_verlauf_u">&nbsp;</div>
        |        </div>
        |    <div class="if5_white_u">&nbsp;</div>
        |    </div>
        |</div>
        |
        |
        |
        |
        |
        |		    			    			    	    <div class="if5_banner" style=""><div class="first"><img alt="" class="illu" height="95" id="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pool/contentelements/cbanner/depotdirekt_bUooyo/bg_gen.jpg" width="180" /><div class="second"><div class="top"><h4><a title="Zum Depot Direkt" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi93ZXJ0cGFwaWVyZV9ib2Vyc2VuaW5mb3Mvd2VydHBhcGllcmRlcG90L3ZvcnRlaWxlL2luZGV4LnBocD9uPSUyRnByaXZhdGt1bmRlbiUyRndlcnRwYXBpZXJlX2JvZXJzZW5pbmZvcyUyRndlcnRwYXBpZXJkZXBvdCUyRnZvcnRlaWxlJTJG"   target="_top">Einfach und komfortabel - Wertpapiere online handeln</a></h4><p class="banner_link_1 c2" id="text">Umfassender Service und g&uuml;nstige Online-Konditionen.</p></div><div class="banner_link_1"><span><a title="Zum Depot Direkt" class="cb_link" id="link" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi93ZXJ0cGFwaWVyZV9ib2Vyc2VuaW5mb3Mvd2VydHBhcGllcmRlcG90L3ZvcnRlaWxlL2luZGV4LnBocD9uPSUyRnByaXZhdGt1bmRlbiUyRndlcnRwYXBpZXJlX2JvZXJzZW5pbmZvcyUyRndlcnRwYXBpZXJkZXBvdCUyRnZvcnRlaWxlJTJG"   target="_top">Zum Depot Direkt</a></span></div></div></div></div>
        |
        |
        |
        |
        |
        |
        |		    			    			    	</div>
        | <div class="if5_content_footer if5_Druckansichtsbuttonseite">
        |<div class="if5_service c5_2 banking"><ul class="service_list"><li class="seite_senden" id="empfehlen"><a class="l3b c0" href="https://banking.berliner-sparkasse.de/portal/portal/_o:c1ac55412183edd7/?r=dd95ec16f03e349e&amp;p=p.finanzstatus&amp;n=%2Fonlinebanking%2Ffinanzstatus%2F" id="empfehlen_link">Finanzstatus</a></li><li class="seite_drucken c0 druck_ohne_js" id="drucken"><script type="text/javascript">
        |  <!--
        |  document.write('<a class="l3b c0" href="javascript:window.print();">Seite drucken<span class="druck_ohne_js" id="druckwrap"><span class="if5_white_o">&nbsp;&nbsp;</span><span class="if5_rand"><span class="if5_verlauf_o">&nbsp;&nbsp;</span><span class="hw_druck_ohne_js">F&uuml;r diese Funktion muss JavaScript aktiviert sein.</span><span class="if5_verlauf_u">&nbsp;&nbsp;</span></span><span class="if5_white_u">&nbsp;&nbsp;</span></span></a>');
        |  //-->
        |  </script>
        |<span style="display:none"><script type="text/javascript" language="JavaScript">/*<!--*/ document.getElementById('druckwrap').className = 'druck_mit_js';document.getElementById('drucken').className = 'seite_drucken c0'; //--></script></span></li><li class="seite_anfang c0"><a class="l3b c0" href="#">Seitenanfang</a></li></ul></div>
        |
        |
        |		    			    			    	   	<div class="if5_footer"><ul class="footer_list l4"><li class="footer_listitem"><a title="Mobile Version" href="https://banking.berliner-sparkasse.de/portal/portal/Starten?IID=10050000&AID=IF5MOBILE"   target="_blank">Mobile Version</a></li><li class="footer_listitem"><a title="BIC: BELADEBE" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvaW1wcmVzc3VtL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRmltcHJlc3N1bSUyRg=="   target="_top">BIC: BELADEBE</a></li><li class="footer_listitem"><a title="Impressum" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvaW1wcmVzc3VtL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRmltcHJlc3N1bSUyRg=="   target="_top">Impressum</a></li><li class="footer_listitem"><a title="AGB" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvYWdiL2FsbGdlbWVpbmVfZ2VzY2hhZWZ0c2JlZGluZ3VuZ2VuL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRmFnYiUyRmFsbGdlbWVpbmVfZ2VzY2hhZWZ0c2JlZGluZ3VuZ2VuJTJG"   target="_top">AGB</a></li><li class="footer_listitem"><a title="Datenschutz" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvZGF0ZW5zY2h1dHovaW5kZXgucGhwP249JTJGbW9kdWxlJTJGc3RhdGljJTJGZGF0ZW5zY2h1dHolMkY="   target="_top">Datenschutz</a></li><li class="footer_listitem"><a title="Preise und Hinweise" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvcHJlaXNlX2hpbndlaXNlL3ZvcnRlaWxlL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRnByZWlzZV9oaW53ZWlzZSUyRnZvcnRlaWxlJTJG"   target="_top">Preise und Hinweise</a></li><li class="footer_listitem"><a title="Sitemap" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zaXRlbWFwL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnNpdGVtYXAlMkY="   target="_top">Sitemap</a></li></ul></div>
        |
        |</div>
        |<!--Footerzu-->
        |</div>
        |<!--Content zu-->
        |
        |		    			    			    	<div class="if5_containerwrapper">
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |<div class="if5_container if5_infobox">
        |  <div class="rounded">
        |    <div class="if5_white_o_rounded">
        |      &nbsp;
        |    </div>
        |    <div class="if5_rand">
        |      <div class="if5_verlauf_o">
        |        &nbsp;
        |      </div>
        |      <div class="rounded_content">
        |        <h4 class="info">
        |            Info-Box
        |        </h4>
        |
        |
        |
        |
        |
        |
        |
        |
        |			<p>
        |				Letzte Anmeldung
        |		 		<br/>
        |				18.11.2013,&nbsp;14:00&nbsp;Uhr
        |			</p>
        |
        |
        |
        |			<p>
        |				Zuletzt benutzte <br/>TAN&nbsp;<a href='/portal/portal/_o:c1ac55412183edd7/?w=PIN_TAN_ADMINISTRATION' class='tanlink'>882676</a>&nbsp;am<br />08.11.2013,&nbsp;09:01&nbsp;Uhr.
        |
        |			</p>
        |
        |
        |
        |
        |            <p id="sdauer"></p>
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |                <div id="direkteinstieg" class="favoriten close">
        |
        |                    <p class="info_favoriten">
        |                        <a href="/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19ndi0xMzA1NTkwODg5RnJhZ21lbnQyN3xlQUNUSU9OPTE9VE9HR0xFX0RFVEFJTF9GQVY_/_o:c1ac55412183edd7/"> Favoriten</a>
        |                    </p>
        |                </div>
        |
        |
        |
        |
        |
        |
        |
        |
        |      </div>
        |      <div class="if5_verlauf_u">
        |        &nbsp;
        |      </div>
        |    </div>
        |    <!-- NI: Verlauf hinzugefügt -->
        |  </div>
        |  <!-- NI: Rand -->
        |  <div class="if5_white_u_rounded">
        |    &nbsp;
        |  </div>
        |  <!-- NI: runde Ecke im Verlauf -->
        |<!-- NI: Ende umschliessendes DIV -->
        |</div>
        |
        |<!--
        |ticktack
        | --><script language="JavaScript" type="text/javascript"><!--
        |var ticktack=12.0*60*1000+new Date().getTime();tick();function tick() {var timeoutID;var s;var t=ticktack-new Date().getTime();t=(t<0)?0:Math.round(t/1000);s=""+ ((t>60)?Math.ceil(t/60).toFixed(0)+" Min.":t.toFixed(0)+" Sek.");document.getElementById("sdauer").innerHTML=" Zu Ihrer Sicherheit erfolgt die automatische Abmeldung in "+s;if (t!=0) timeoutID = window.setTimeout("tick()",(t>60) ? 6000 : 1000);else {window.clearTimeout(timeoutID);location.replace("/portal/portal/_o:c1ac55412183edd7/LogoutTimeout");}}
        | --></script>
        |
        |
        |
        |		    			    			    	    <div class="if5_container if5_kontakt" id="if5_kontakt_container" style=""><div class="rounded"><div class="if5_white_o_rounded">&nbsp;</div><div class="if5_rand"><div class="if5_verlauf_o">&nbsp;</div><div class="rounded_content"><h5 class="kontakt"><span>Telefon</span></h5><h6><span id="telefon_nr">030/869 869 57</span><!--<span class="required" id="preis_req">*</span>--></h6><div class="analyst" id="if5_kontakt_container_analyst" style="display:none"><h5 class="head" id="if5_kontakt_container_analyst_head"><span>Sie werden beraten von:</span></h5><ul class="kontakt_list datablock" id="if5_kontakt_container_analyst_data">&nbsp;</ul></div><div class="standard" id="if5_kontakt_container_standard"><h5 class="head" id="if5_kontakt_container_standard_head" style="display:none"><span>Unser Service f&uuml;r Sie:</span></h5><ul class="kontakt_list datablock" id="if5_kontakt_container_standard_data"><li class="kontakt_email l4" id="if5_kontakt_container_standard_email"><a href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9rb250YWt0L2tvbnRha3RfZW1haWxfZm9ybXVsYXIvaW5kZXgucGhwP249JTJGbW9kdWxlJTJGa29udGFrdCUyRmtvbnRha3RfZW1haWxfZm9ybXVsYXIlMkYmc2hhMjU2a2V5PXNHN0NETDdZOHpYMUlQeWlMMjhIemVHb3JhZGdEVUkxWGVGZW1URFIlMkY2QSUzRCZkYXRhPVRXRnBiRVZ0Y0daaFpXNW5aWEk5WW1GdWEybHVaMEJpWlhKc2FXNWxjaTF6Y0dGeWEyRnpjMlV1WkdVS1RXRnBiRUpsZEhKbFptWTlSUzFOWVdsc0xVRnVabkpoWjJVNklFOXViR2x1WlMxQ1lXNXJhVzVuQ2tWeVpXbG5ibWx6ZEhsd1RtbGphSFJHWVd4c1lXSnpZMmhzYVdWemMyVnVaRDBLUlhKbGFXZHVhWE4wZVhCQmJtOXVlVzA5Q25CcFpEMDBNREElM0QmYW1wO0tPTlRBS1RGT1JNPTE=" id="termin" title="Termin vereinbaren">Termin vereinbaren</a></li>
        |	                       		<!--<li class="kontakt_rueckruf l4" id="li_rueckruf"><a href="" id="rueckruf" title=""></a></li>-->
        |		                        <li class="kontakt_filiale l4" id="li_filiale"><a href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9paHJlX3NwYXJrYXNzZS9maWxpYWxmaW5kZXIvaW5kZXgucGhwP249JTJGbW9kdWxlJTJGaWhyZV9zcGFya2Fzc2UlMkZzdGFuZG9ydGUtZGVyLWJlcmxpbmVyLXNwYXJrYXNzZSUyRmZpbGlhbGZpbmRlciUyRg==" id="filiale" title="Öffnungszeiten">&Ouml;ffnungszeiten</a></li>
        |		                        <li class="kontakt_notfall l4" id="li_notfall"><a href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9rb250YWt0L2tvbnRha3RfcnVmbnVtbWVybi9pbmRleC5waHA/bj0lMkZtb2R1bGUlMkZrb250YWt0JTJGa29udGFrdF9ydWZudW1tZXJuJTJG" id="notfall" title="Wichtige Rufnummern">Wichtige Rufnummern</a></li><!--<li class="kontakt_berater l4" id="li_chat"><a href="" id="chat" target="_blank" title=""></a></li>-->
        |		                        <li class="kontakt_newsletter l4" id="li_newsletter"><a href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9paHJlX3NwYXJrYXNzZS9uZXdzbGV0dGVyL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRmlocmVfc3Bhcmthc3NlJTJGbmV3c2xldHRlciUyRg==" id="newsletter" title="Newsletter">Newsletter</a></li>
        |			                			<li class="kontakt_social_networks l4" id="li_social_networks"><a href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9paHJlX3NwYXJrYXNzZS9zb2NpYWxfbWVkaWEvdm9ydGVpbGUvaW5kZXgucGhwP249JTJGbW9kdWxlJTJGaWhyZV9zcGFya2Fzc2UlMkZzb2NpYWxfbWVkaWElMkZ2b3J0ZWlsZSUyRg==" id="newsletter" title="Social Networks">Social Networks</a></li><li class="kontakt_socialnetul l4" id="li_socialNetul"><ul class="socialnet_list"><li class="socialnet_list_li hovercontainer"><a title="Facebook" id="item0" href="https://www.facebook.com/berlinersparkasse"   target="_blank"><img class="mouseout" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_sm_kt_facebook.png" /><img class="mousein" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_sm_kt_facebook_g.png" /></a></li><li class="socialnet_list_li hovercontainer"><a title="Twitter" id="item1" href="https://www.twitter.com/BerlinerSPK"   target="_blank"><img class="mouseout" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_sm_kt_twitter.png" /><img class="mousein" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_sm_kt_twitter_g.png" /></a></li><li class="socialnet_list_li hovercontainer"><a title="https://www.youtube.com/user/berlinersparkasse" id="item2" href="https://www.youtube.com/user/berlinersparkasse"   target="_blank"><img class="mouseout" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_sm_kt_youtube.png" /><img class="mousein" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_sm_kt_youtube_g.png" /></a></li><li class="socialnet_list_li hovercontainer"><a title="Berliner Sparkasse" id="item3" href="https://www.berliner-sparkasse.de"   target="_blank"><img class="mouseout" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/bg_weiss_transp.png" /><img class="mousein" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/bg_weiss_transp.png" /></a></li><li class="socialnet_list_li hovercontainer"><a title="Xing" id="item4" href="https://www.xing.com/net/gruenderclubberlin"   target="_blank"><img class="mouseout" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_sm_kt_xing.png" /><img class="mousein" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_sm_kt_xing_g.png" /></a></li><li class="socialnet_list_li hovercontainer"><a title="Service-Community" id="item5" href="https://kundenservice.berliner-sparkasse.de"   target="_blank"><img class="mouseout" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/servicecommunity_1717.png" /><img class="mousein" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/servicecommunity_1717_grey.png" /></a></li></ul></li></ul></div><!--<div class="preis" id="preis"><p>*<span id="preisangabe_span">&nbsp;</span></p></div>--></div><div class="if5_verlauf_u">&nbsp;</div></div><div class="if5_white_u_rounded">&nbsp;</div></div></div><script type="text/javascript" language="JavaScript" src="/ifdata/10050000/IPSTANDARD/4/content/www/js/kontakt.js"><!-- --></script><script language="JavaScript" type="text/javascript">try {
        |		    KontaktContainer.getInstance({
        |		    	showAnalystImage: true,
        |		    	requireImage: true,
        |		    	hideStandardEmail: true,
        |		    	analystEmailText: 'E-Mail schreiben',
        |		    	analystEmail: '',
        |		    	analystEmailRawLink: 'https://banking.berliner-sparkasse.de/portal/portal/_o:c1ac55412183edd7/?r=ec922d4070f4281f&p=p.berater_e_mail1z7NAo&n=%2Fonlinebanking%2Fv_formulare%2Fberater_e_mail%2F&dparam=dummy',
        |		    	analystEmailLink: 'https://banking.berliner-sparkasse.de/portal/portal/_o:c1ac55412183edd7/?r=ec922d4070f4281f&p=p.berater_e_mail1z7NAo&n=%2Fonlinebanking%2Fv_formulare%2Fberater_e_mail%2F&dparam=dummy&amp;data=TWFpbEJldHJlZmY9TmV1ZSBOYWNocmljaHQgw7xiZXIgZGVuIEtvbnRha3Rjb250YWluZXIgbWl0IGRlciBGdW5rdGlvbiBCZXJhdGVyYmlsZApFcmVpZ25pc3R5cE5pY2h0RmFsbGFic2NobGllc3NlbmQ9U0RJUkVLVC1FUkxFCkVyZWlnbmlzdHlwQW5vbnltPVNESVJFS1QtRVJMRQpwaWQ9NDAwCgpGb3JtRF9TZW5kVG89Cg%3D%3D&sha256key=2nU6C1pwaQLrHRf1XoJ5nVUmGW7eCyoWrwlSwhMm%2BwA%3D',
        |		    	autoShrinkFont: true,
        |		    	analystData: {
        |		    		name: '',
        |		    		name2: '',
        |		    		position: '',
        |		    		position2: '',
        |		    		unit: 'PrivatkundenCenter',
        |		    		unit2: '',
        |		    		address: 'Am Treptower Park 14',
        |		    		place: '12435 Berlin',
        |		    		image: '/ifdata/10050000/IPSTANDARD/4/content/www/pixel/beraterbilder_export/9239deb45959ca3d651a182e7c7ae6d8ff9d99f2803358415a9ea0a5743e6570.png',
        |		    		telefon: '030 869-86969',
        |		    		fax: '030 869-741014'
        |		    	},
        |		    	analystInfo: {'fax':'false','name':'true','name2':'false','position':'true','position2':'false','unit':'true','unit2':'false','address':'true','place':'true','image':'true','telefon':'true','charge':'false'},
        |		    	kontaktRender: {'showAnalystInfo':'all','canCollapse':'true'}
        |		    });
        |	    } catch(e) {};</script>
        |
        |
        |
        |
        |
        |
        |		    			    			    	    <div class="if5_container if5_werbung" style=""><div class="rounded"><div class="if5_white_o_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --><div class="if5_rand"><div class="if5_verlauf_o">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --><!--  Backgroundimage wird per Inline-Style gesetzt, da Aktionsfl&auml;che   --><div class="rounded_content"><h4><span>Plussparen</span></h4><div class="clip"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pool/contentelements/container/Plussapren_wKCQio/bg_gen.jpg" /></div><div class="textsection" style=""><h3><span></span></h3><p>Sichern Sie sich Zinsen f&uuml;r ungenutzte Girokonto-Guthaben!</p><div class="if5_container_link"><div><a title="Weitere Informationen" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9zcGFyZW5fYW5sZWdlbi9wbHVzX3NwYXJlbi92b3J0ZWlsZS9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkZzcGFyZW5fYW5sZWdlbiUyRnBsdXNfc3BhcmVuJTJGdm9ydGVpbGUlMkY="   target="_top">Weitere Informationen</a></div></div></div></div><div class="if5_verlauf_u">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --></div><!--  NI: Rand  --><div class="if5_white_u_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --></div><!--  NI: Ende umschliessendes DIV  --></div>
        |
        |
        |
        |
        |
        |
        |		    			    			    	    <div class="if5_container if5_werbung" style=""><div class="rounded"><div class="if5_white_o_rounded">&nbsp;</div><div class="if5_rand"><div class="if5_verlauf_o">&nbsp;</div><div class="rounded_content"><h4><span>Ab 2014</span></h4><div class="clip" style="background:url(/ifdata/10050000/IPSTANDARD/4/content/www/pool/contentelements/container/sepa_info_allg_NMFUgp/bg_gen.jpg) center center no-repeat; height: 154px;"><div style="color: #FF0000; display: inline-block; font-size: 1.8em; font-weight: bold; padding: 1em 0 0 0.2em; width: 100%;">nur noch<br />&nbsp;<span class="pit_countdown_datum" style="font-size: 150%; padding-left: 0.2em;">01.02.2014</span></div></div><div class="textsection" style=""><h3><span>SEPA ver&auml;ndert den Zahlungsverkehr:</span></h3><p><br />Alle &Uuml;berweisungen und Lastschriften in Euro innerhalb Deutschlands sind dann nach europaweit einheitlichen Verfahren vorzunehmen.
        |<br /><br /></p><div class="if5_container_link"><div><a title="Für Privatkunden" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9rb250ZW5fa2FydGVuL3NlcGEvdm9ydGVpbGUvaW5kZXgucGhwP249JTJGcHJpdmF0a3VuZGVuJTJGa29udGVuX2thcnRlbiUyRnNlcGElMkZ2b3J0ZWlsZSUyRg=="   target="_top">F&uuml;r Privatkunden</a></div></div><div class="if5_container_link"><div><a title="Für Firmenkunden" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2Zpcm1lbmt1bmRlbi9rb250ZW5fa2FydGVuL3NlcGEvdWViZXJibGljay9pbmRleC5waHA/bj0lMkZmaXJtZW5rdW5kZW4lMkZrb250ZW5fa2FydGVuJTJGc2VwYSUyRnVlYmVyYmxpY2slMkY="   target="_top">F&uuml;r Firmenkunden</a></div></div></div></div><div class="if5_verlauf_u">&nbsp;</div></div><div class="if5_white_u_rounded">&nbsp;</div></div></div><script language="JavaScript" type="text/javascript"><!--  Begin
        |			var datum_string = '', datum_array='', jahr='', monat='', tag='', zielDatum= '';
        |			var startDatum=new Date();
        |			var tage=0;
        |			jQuery('.pit_countdown_datum').each(function(){
        |				datum_string = jQuery(this).html();
        |				if(datum_string.match(/[0-3][0-9]\.[0-1][0-9]\.2[0-9]{3}/)){
        |					datum_array = datum_string.split(".");
        |					jahr=datum_array[2], monat=datum_array[1], tag=datum_array[0];
        |					zielDatum=new Date(jahr,monat-1,tag,0,0,0);
        |					tage=0;
        |					if(startDatum<zielDatum)	{
        |						var tage=1;
        |						while(startDatum.getTime()+(24*60*60*1000)<zielDatum) {
        |							tage++;
        |							startDatum.setTime(startDatum.getTime()+(24*60*60*1000));
        |						}
        |					}
        |					jQuery(this).html(tage + ' Tag' + ((tage!=1)? 'e': ''));
        |				}
        |			});
        |		//  End  --></script>
        |
        |
        |
        |
        |
        |
        |		    			    			    	    <div class="if5_container if5_aktion_cont" style=""><div class="rounded"><div class="if5_white_o_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --><div class="if5_rand"><div class="if5_verlauf_o">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --><div class="rounded_content"><h4><span>Fragen offen? Feedback? Ideen?</span></h4><div class="clip"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/container/bsk_co153_servicecommunity.jpg" /></div><p>Frage zum Online-Banking? Idee, was noch besser werden k&ouml;nnte? Schreiben Sie uns jetzt gleich!</p><div class="if5_container_link"><div><a title="Service-Community" href="http://s.de/9g2"   target="_blank">Service-Community</a></div></div></div><div class="if5_verlauf_u">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --></div><!--  NI: Rand  --><div class="if5_white_u_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --></div><!--  NI: Ende umschliessendes DIV  --></div>
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |
        |</div>
        |
        |
        |		    			    		    			    		  		  				<br />
        |	</div>
        |	</div>
        |	    <!-- Slot slot.peel (leer) -->
        |<br style="clear:both;"/>
        |  <form name='portletform' action="" method="post">
        |    <input type='hidden' name='portlets'/>
        |    <input type='hidden' name='page' value='org.apache.jetspeed.om.page.impl.ContentPageImpl@4698fd56'/>
        |  </form>
        |
        |</body>
        |</html>
      """.stripMargin
      val data = new SparKassenClient().parseOverview(html)
      println(data)
      data.accounts.get("Tagesgeldkonto Direkt").get.value must beEqualTo(21000.00)
    }
    "parse stock overview" in {
      val html = """
                   |<?xml version="1.0" encoding="utf-8"?>
                   |<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                   |<html xmlns="http://www.w3.org/1999/xhtml" lang="de">
                   |<head>
                   |	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
                   |<!--
                   |	<base href="http://banking.berliner-sparkasse.de:80/portal/">
                   |-->
                   |	<meta http-equiv="Content-style-type" content="text/css" />
                   |		<script type="text/javascript" src="/ifdata/10050000/IPSTANDARD/3/content/www/js/std.js"></script>
                   |	<script type="text/javascript" src="/ifdata/10050000/IPSTANDARD/3/content/www/js/fi/jquery.min.js"></script>
                   |	<script type="text/javascript" src="/ifdata/10050000/IPSTANDARD/3/content/www/js/fi/jquery.ui.min.js"></script>
                   |	<script type="text/javascript" src="/ifdata/10050000/IPSTANDARD/3/content/www/js/fi/jquery.easing.min.js"></script>
                   |
                   |	 <script type="text/javascript"><!--
                   |    function helpMe(Adresse, Titel) {
                   |  		var myHelp = window.open(Adresse, Titel, 'width=450,height=300,resizable=yes,status=no,scrollbars=yes');
                   |  		myHelp.focus();
                   |	}
                   |	function helpMe2(Adresse, Titel) {
                   |  		var myHelp = window.open(Adresse, Titel, 'status=no,scrollbars=yes');
                   |  		myHelp.focus();
                   |	}
                   | //--></script>
                   |	 <style type="text/css"><!--
                   |             @import url("/ifdata/10050000/IPSTANDARD/3/content/www/css/if5_raster.css");
                   |                 @import url("/ifdata/10050000/IPSTANDARD/3/content/www/css/if5_container.css");
                   |                 @import url("/ifdata/10050000/IPSTANDARD/3/content/www/css/if5_banking.css");
                   |                     --></style>
                   |
                   |                             <!--[if IE 6]>
                   |        <link rel="stylesheet" href="/ifdata/10050000/IPSTANDARD/3/content/www/css/if5_ie6.css" type="text/css" media="all" />
                   |     <![endif]-->
                   |               <!--[if IE 7]>
                   |         <link rel="stylesheet" href="/ifdata/10050000/IPSTANDARD/3/content/www/css/if5_ie7.css" type="text/css" media="all" />
                   |     <![endif]-->
                   |                      <link rel="stylesheet" media="print" href="/ifdata/10050000/IPSTANDARD/3/content/www/css/if5_druck.css" type="text/css" />
                   |              <link rel="stylesheet" href="/css/opttan4_0_3.css" type="text/css" />
                   |
                   |			<link rel="shortcut icon" type="image/x-icon" href="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/favicon.ico" />
                   |        <link rel="icon" type="image/x-icon" href="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/favicon.ico" />
                   |	<title>Berliner Sparkasse (10050000) - Depotaufstellung</title>
                   |	<meta name="keywords" content="Brokerage,Depotaufstellung,Depotübersicht" />
                   |	<meta name="description" content="Die Internet-Filiale der Berliner Sparkasse: Online-Banking, Rente, Aktien, Baufinanzierung, ImmobilienangeboteDie Internet-Filiale der Berliner Sparkasse: Online-Banking, Rente, Aktien, Baufinanzierung, Immobilienangebote" />
                   |			  	<META HTTP-EQUIV="Refresh" CONTENT="720; URL=/portal/portal/_o:33d4ef7be9088a84/LogoutTimeout" />
                   | </head>
                   |<body class=   "if5"
                   |    onload="">
                   |	   <script language="javascript" type="text/javascript">
                   |  	 	try{ifPage = new IfPage({showFontSizeControl:true,editmode:false,istx:true, pzurl:'https://www.berliner-sparkasse.de/',isElexir:false}) }catch(e){}
                   |  </script>
                   |
                   |
                   |<div id="js_VelocityOneColumn_" class="if5_outer">
                   |
                   |
                   |
                   |
                   |  	<div id="column_VelocityOneColumn_0"
                   |	     class="if5_inner">
                   |
                   |
                   |
                   |		    			    			    	<div class="if5_header">
                   |   	<!--  create Href aus &uuml;bergebenen Strings  --><div class="if5_logo_spk b1"><a href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkY=" target="_top"><img alt="Berliner Sparkasse - Privatkunden" class="logo" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/logo_spk/if5_spk_logo.gif" title="Berliner Sparkasse - Privatkunden" /></a></div>
                   |
                   |
                   |		    			    			    	    <div class="if5_gsw b5" style="background: url(/ifdata/10050000/IPSTANDARD/3/content/www/pool/contentelements/gsw/Weihnachtskredit_2013_FCIZep/op_gen.jpg) no-repeat scroll right top;"><div class="gsw_text"><h2 class="gsw_h1"><a title="Informieren sie sich jetzt zum S-Privatkredit" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9rcmVkaXRlX2xlYXNpbmcvcHJpdmF0a3JlZGl0X3NrcC92b3J0ZWlsZS9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkZrcmVkaXRlX2xlYXNpbmclMkZwcml2YXRrcmVkaXRfc2twJTJGdm9ydGVpbGUlMkYmYW1wO2N0cD04YjExZTgwOGM2YTdmOWNjZGVmODk0NTNhYzJlMzUyNw=="   target="_top">Heimkino, Reise, neue K&uuml;che:<br />Der S-Privatkredit</a></h2><div class="gsw_weiter_link"><p class="l2 weiter_linktext"><a title="Informieren sie sich jetzt zum S-Privatkredit" class="c1" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9rcmVkaXRlX2xlYXNpbmcvcHJpdmF0a3JlZGl0X3NrcC92b3J0ZWlsZS9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkZrcmVkaXRlX2xlYXNpbmclMkZwcml2YXRrcmVkaXRfc2twJTJGdm9ydGVpbGUlMkYmYW1wO2N0cD04YjExZTgwOGM2YTdmOWNjZGVmODk0NTNhYzJlMzUyNw=="   target="_top">Mehr Infos</a></p></div></div></div>
                   |
                   |</div>
                   |
                   |
                   |		    			    			    		<div class="if5_metanavi">
                   |    	<div class="metanavi_blz"><p class="blz">BLZ: 10050000</p></div>
                   |
                   |
                   |
                   |		    			    			    	    <div class="metanavi_navi"><ul class="metanavi_list"><li class="metanavi_listitem"><a title="Über uns" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9paHJlX3NwYXJrYXNzZS9pbmRleC5waHA/bj0lMkZtb2R1bGUlMkZpaHJlX3NwYXJrYXNzZSUyRiZhbXA7Y3RwPWViNGZjM2FiMWE5MTcyMjE4YzIyYWU2MTU3NTc4OTYy"   target="_top">&Uuml;ber uns</a></li><li class="metanavi_listitem"><a title="Standorte der Berliner Sparkasse" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9paHJlX3NwYXJrYXNzZS9zdGFuZG9ydGUtZGVyLWJlcmxpbmVyLXNwYXJrYXNzZS9kZXRhaWxzL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRmlocmVfc3Bhcmthc3NlJTJGc3RhbmRvcnRlLWRlci1iZXJsaW5lci1zcGFya2Fzc2UlMkZkZXRhaWxzJTJGJmFtcDtjdHA9MDZmYmUwYmFlZmVkZTRhNmZmZWM0MmRhNGI5OWIzOTQ="   target="_top">Standorte</a></li><li class="metanavi_listitem"><a title="Kontakt" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9rb250YWt0L2tvbnRha3R3ZWljaGUvaW5kZXgucGhwP249JTJGbW9kdWxlJTJGa29udGFrdCUyRktvbnRha3R3ZWljaGUlMkYmYW1wO2N0cD02M2FiYmJmZDI2OTE5NTA2MWE2NTNjMGZiMDEzZTVhMg=="   target="_top">Kontakt</a></li><li class="metanavi_listitem"><a title="Hilfe" href="http://s.de/an5"   target="_blank">Hilfe</a></li><li class="metanavi_listitem"><a title="Karriere" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2thcnJpZXJlL2luZGV4LnBocD9uPSUyRmthcnJpZXJlJTJGJmFtcDtjdHA9N2NjYmI1MTFiNTM4YTQyYjI5YWZiZDA0ODY2N2E0OTA="   target="_top">Karriere</a></li><li class="metanavi_listitem"><a title="Shop" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9iYW5raW5nL3NwYXJrYXNzZW5zaG9wL2ltLXNwYXJrYXNzZW5zaG9wLWZpbmRlbi1zaWUvaW5kZXgucGhwP249JTJGcHJpdmF0a3VuZGVuJTJGYmFua2luZyUyRnNwYXJrYXNzZW5zaG9wJTJGaW0tc3Bhcmthc3NlbnNob3AtZmluZGVuLXNpZSUyRiZhbXA7Y3RwPTYyMTNhMDU3YWYyN2JjZmQ4MGUzOTdhZjhmOGMwNzBm"   target="_top">Shop</a></li><li class="metanavi_listitem"><a title="Videos" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9tZWRpYWNlbnRlci91ZWJlcnNpY2h0L2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRm1lZGlhY2VudGVyJTJGdWViZXJzaWNodCUyRiZhbXA7Y3RwPWY3YjcxNDE0MDQ2OGZmMGQ5YWRlMWVmOGZiZmVjODAz"   target="_top">Videos</a></li></ul><div class="fontsize" id="metanaviloggedinfontsize" style="display:none"><ul style="background:no-repeat;"><li _size="62.5" _stat="statnormal.png" title="Schrift normal"><img alt="Schrift normal" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/fs0.png" /></li><li _size="68.8" _stat="statgroesser.png" title="Schrift größer"><img alt="Schrift größer" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/fs1.png" /></li><li _size="75" _stat="statgross.png" title="Schrift groß"><img alt="Schrift groß" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/fs2.png" /></li></ul></div><script type="text/javascript" language="JavaScript" src="/ifdata/10050000/IPSTANDARD/3/content/www/js/fontsize.js"><!-- --></script><script language="JavaScript" type="text/javascript">try {new FontSizeControl('metanaviloggedinfontsize',{mode:3});} catch(e){}</script></div>
                   |
                   |
                   |
                   |		    			    			    	<div class="metanavi_suche"><div class="suchergebnis" id="suchergebnis"></div><form accept-charset="UTF-8" action="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdWNoZS9pbmRleC5waHA=" class="suche" id="suchformular" method="post" target="_top"><input id="suchfeld" type="text" name="words" value="Suchbegriff" size="10" maxlength="50" class="suche_feld is" onmouseover="glSearch(this)" onblur="testField(this, this.firstValue)" onfocus="testField(this, this.firstValue)" /><input type="hidden" name="dummy" value="&#9760;"/>
                   |							<input type="image" name="suchbutton" value="Suchen" alt="Suchen" title="Suchen" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/metanavi_weiter_button.gif" class="suche_button c0 l3" onmouseover="glSearch(document.forms.suchformular.suchfeld)"/>
                   |							<script id="suche_script" src="/ifdata/10050000/IPSTANDARD/3/content/www/js/suche.js" type="text/javascript"></script></form></div>
                   |
                   |</div>
                   |
                   |
                   |
                   |
                   |
                   |		    			    			    	<div class="if5_nav b4">
                   |    	<span style='display:none;'>ausgew&auml;hlt: Online-Banking: Brokerage: Depotanzeige</span><ul class='nav0wrap'><li class='nav0item open active withsub'><h3><a target='_top' title='Online-Banking' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.finanzstatus&amp;n=%2Fonlinebanking%2Ffinanzstatus%2F'>Online-Banking</a></h3><form action='/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19UcmVlTmF2aWdhdGlvblBvcnRsZXR8Zg__/_o:33d4ef7be9088a84/?jJTmdxXxNXoTAnXp.x=1' class='tx_anmelden' method='post' accept-charset='UTF-8' autocomplete='off' onsubmit="javascript:if(this.id == null || this.id =='') {this.id='1';return true;} else {window.alert('Ihre Daten wurden bereits abgesendet!');return false;};"><fieldset><p> Frank Ittermann</p><input type='image' title='Sicher abmelden' alt='Abmelden' name='anmeldebutton' src='/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/if5_abmelden.png'/></fieldset></form><form action='/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19UcmVlTmF2aWdhdGlvblBvcnRsZXR8Zg__/_o:33d4ef7be9088a84/' class='tx_anmelden' method='post' accept-charset='UTF-8' autocomplete='off' onsubmit="javascript:if(this.id == null || this.id =='') {this.id='1';return true;} else {window.alert('Ihre Daten wurden bereits abgesendet!');return false;};"><fieldset><input type='hidden' id='direkt' name='direkt' value='true'/><div class='direkteinstieg'><label for='pageid'>direkt zu:</label><select id='pageid' name='p'><option value='/onlinebanking/finanzstatus/' selected='selected'>Finanzstatus</option><option value='/onlinebanking/umsaetze/umsatzabfrage_neu/'>Ums&auml;tze</option><option value='/onlinebanking/banking/ueberweisung/sepa_einzelueberweisung/'>&Uuml;berweisung</option><option value='/onlinebanking/banking/komfortueberweisung/'>&Uuml;bertrag</option><option value='/onlinebanking/banking/dauerauftrag_neu/'>Dauerauftrag</option><option value='/onlinebanking/banking/handy_laden/'>Handy laden</option></select><input type='image' title='Direkt zu...' alt='Weiter' name='direktZuButton' src='/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/primlink_navi.gif'/></div></fieldset></form><ul class='nav1wrap'><li class='nav1item'><h4><a target='_top' title='Finanzstatus' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.finanzstatus&amp;n=%2Fonlinebanking%2Ffinanzstatus%2F'>Finanzstatus</a></h4></li><li class='nav1item'><h4><a target='_top' title='Ums&auml;tze' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.umsatzabfrage_neuyhaZcp&amp;n=%2Fonlinebanking%2Fumsaetze%2Fumsatzabfrage_neu%2F'>Ums&auml;tze</a></h4></li><li class='nav1item'><h4><a target='_top' title='Kontoauszug' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.kontoauszug&amp;n=%2Fonlinebanking%2Fkontoauszug%2F'>Kontoauszug</a></h4></li><li class='nav1item'><h4><a target='_top' title='Banking' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.sepa_einzelueberweisung&amp;n=%2Fonlinebanking%2Fbanking%2Fueberweisung%2Fsepa_einzelueberweisung%2F'>Banking</a></h4></li><li class='nav1item active withsub'><h4><a target='_top' title='Brokerage' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.depotaufstellung&amp;n=%2Fonlinebanking%2Fbrokerage%2Fdepot_uebersicht%2Fdepotaufstellung%2F'>Brokerage</a></h4><ul class='nav2wrap'><li class='nav2item active current'><h5><a target='_top' title='Depotanzeige (ausgew&auml;hlt)' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.depotaufstellung&amp;n=%2Fonlinebanking%2Fbrokerage%2Fdepot_uebersicht%2Fdepotaufstellung%2F'>Depotanzeige <span style='display:none;'>(ausgew&auml;hlt)</span></a></h5></li><li class='nav2item'><h5><a target='_top' title='Orderbuch' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.orderstatus&amp;n=%2Fonlinebanking%2Fbrokerage%2Forderbuch%2F'>Orderbuch</a></h5></li><li class='nav2item'><h5><a target='_top' title='Kauf' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.neukauf&amp;n=%2Fonlinebanking%2Fbrokerage%2Fneukauf%2F'>Kauf</a></h5></li><li class='nav2item'><h5><a target='_top' title='Verkauf' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.wertpapier_verkauf&amp;n=%2Fonlinebanking%2Fbrokerage%2Fwertpapier_verkauf%2F'>Verkauf</a></h5></li><li class='nav2item'><h5><a target='_top' title='Neuemission' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.neuemissionen&amp;n=%2Fonlinebanking%2Fbrokerage%2Fneuemissionen%2F'>Neuemission</a></h5></li><li class='nav2item'><h5><a target='_blank' title='B&ouml;rsenCenter' href='https://web.s-investor.de/app/markt.htm?INST_ID=0004093'>B&ouml;rsenCenter</a></h5></li></ul></li><li class='nav1item'><h4><a target='_top' title='Kreditkarte' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.kreditkartendetails&amp;n=%2Fonlinebanking%2Fkreditkarte%2Fkartendetails%2F'>Kreditkarte</a></h4></li><li class='nav1item'><h4><a target='_top' title='Postfach' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.messages&amp;n=%2Fonlinebanking%2Fpostfach%2Fmessages%2F'>Postfach</a></h4></li><li class='nav1item'><h4><a target='_top' title='Offene Auftr&auml;ge' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.terminauftraege&amp;n=%2Fonlinebanking%2Foffene_auftraege%2F3ebene%2Fterminauftraege%2F'>Offene Auftr&auml;ge</a></h4></li><li class='nav1item'><h4><a target='_top' title='Sicherheit' href='/portal/portal/_o:33d4ef7be9088a84/?p=p.pin_tan_verwaltung&amp;n=%2Fonlinebanking%2Fsicherheit_angemeldet%2Fpin_tan_verwaltung%2F'>Sicherheit</a></h4></li><li class='nav1item '><h4><a target='_top' title='Aktuelles und Service' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9iYW5raW5nL2FrdHVlbGxlLWhpbndlaXNlL2FyY2hpdl8yMDEzL2luZGV4LnBocD9uPSUyRm9ubGluZWJhbmtpbmclMkZzZXJ2aWNlJTJGYWt0dWVsbGVzJTJGMjAxMyUyRg=='>Aktuelles und Service</a></h4></li></ul></li><li class='nav0item close'><h3><a target='_top' title='Online-Produkte' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL29ubGluZS1wcm9kdWt0ZS9ob21lcGFnZS9pbmRleC5waHA/bj0lMkZvbmxpbmUtcHJvZHVrdGUlMkY='>Online-Produkte</a></h3></li><li class='nav0item close'><h3><a target='_top' title='Privatkunden' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkY='>Privatkunden</a></h3></li><li class='nav0item close'><h3><a target='_top' title='Firmenkunden' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2Zpcm1lbmt1bmRlbi9pbmRleC5waHA/bj0lMkZmaXJtZW5rdW5kZW4lMkY='>Firmenkunden</a></h3></li><li class='nav0item close '><h3><a target='_top' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2ZpbmFuemtvbnplcHQvaW5kZXgucGhwP249JTJGc3Bhcmthc3Nlbi1maW5hbnprb256ZXB0JTJG'>Sparkassen-Finanzkonzept</a></h3></li><li class='nav0item open withsub'><h3><a target='_top' title='Spezielle Angebote' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL2p1bmdlLWxldXRlL2luZGV4LnBocD9uPSUyRnppZWxncnVwcGVuJTJGanVuZ2UtbGV1dGUlMkY='>Spezielle Angebote</a></h3><ul class='nav1wrap'><li class='nav1item'><h4><a target='_top' title='Junge Leute' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL2p1bmdlLWxldXRlL2luZGV4LnBocD9uPSUyRnppZWxncnVwcGVuJTJGanVuZ2UtbGV1dGUlMkY='>Junge Leute</a></h4></li><li class='nav1item'><h4><a target='_top' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL3ByaXZhdGUtYmFua2luZy9pbmRleC5waHA/bj0lMkZ6aWVsZ3J1cHBlbiUyRnByaXZhdGUtYmFua2luZyUyRg=='>Private Banking</a></h4></li><li class='nav1item '><h4><a target='_top' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL3MtdmVyZWluL2luZGV4LnBocD9uPSUyRnppZWxncnVwcGVuJTJGcy12ZXJlaW4lMkY='>Vereine</a></h4></li><li class='nav1item '><h4><a target='_blank' title='Mobile Beratung' href='https://service.berliner-sparkasse.de/msgen/mobile-beratung/'>Mobile Beratung</a></h4></li></ul></li></ul>
                   |</div>
                   |
                   |		    			    			    	<div class="if5_content if5_banking">
                   | 	  	<div class="if5_content_inner">
                   |
                   |
                   |
                   |
                   |   <div class="contentbereich">
                   |    <a title="Contentbereich / Seiteninhalt" name="pagecontent" href="#" accesskey="3"></a>
                   |
                   |    <h2 class="contentbereichHeadLine">Depotanzeige</h2>
                   |
                   |
                   | <!-- der Rest folgt in "if5portletTransactionStartVol2" -->
                   |
                   |
                   |
                   |
                   |		    			    			    	 <!-- ohne "if5portletTransactionStartVol1" funktioniert hier nichts -->
                   |
                   |
                   |    <div> <!-- Wrapper -->
                   |        <a title="Binnennavigation" name="pagesubnavigation" href="#" accesskey="4"></a>
                   |        <h3 class="invisible" style="display:none">Binnennavigation</h3>
                   |    </div>
                   |    <div class="if5_seiten">
                   |         <div class="if5_white_o">&nbsp;</div>
                   |         <div class="if5_rand">
                   |      <div class="if5_verlauf_o">&nbsp;</div>
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |<div class="contentcontainerMainLayout"><!-- ContentContainerMainLayout auf -->
                   |
                   |    <SCRIPT LANGUAGE="javascript">
                   |    <!--
                   |    function jumpToAnchor(param) {
                   |        if (param != 'null') {
                   |            window.location.hash = '#' + param;
                   |        }
                   |    }
                   |    -->
                   |    </SCRIPT>
                   |
                   |    <form id="698763a53994d83f" method="post" action="/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19ldm9HdlBvcnRsZXR8YzB8ZDF8ZV9zcGFnZT0xPS9Db250cm9sbGVyLmRv/_o:33d4ef7be9088a84/" onsubmit="return teste();">
                   |<script type="text/javascript">
                   |var submitTime = 0;
                   |function teste() {
                   |	if (submitTime == 0) {
                   |		submitTime = new Date().getTime();
                   |	}
                   |	field = document.getElementById('opeiqRltzonDvnoN');
                   |	if ( field.value == '0' ) {
                   |		fieldNoCheck = document.getElementById('gDkWekTQDrbXGEps');
                   |		if ( fieldNoCheck.value == '0' ) {
                   |			field.value = '1';
                   |			return true;
                   |		} else {
                   |			fieldNoCheck.value = '0';
                   |			return true;
                   |		}
                   |	} else {
                   |		if(!(/Firefox[\/\s](\d+\.\d+)/.test(navigator.userAgent)) || new Date().getTime() - submitTime > 1000) {
                   |			window.alert('Ihre Daten wurden bereits abgesendet!');
                   |			return false;
                   |		}
                   |	}
                   |}
                   |
                   |function doNoCheck() {
                   |	fieldNoCheck = document.getElementById('gDkWekTQDrbXGEps');
                   |	fieldNoCheck.value = '1';
                   |}
                   |
                   |function makeKalenderButtonForElementVisible(elementName) {
                   |	try {
                   |		var datumsFeld = document.getElementsByName(elementName)[0];
                   |		datumsFeld.nextSibling.style.display = "inline";
                   |		datumsFeld.nextSibling.style.visibility = "visible";
                   | 	}
                   |	catch (e) {}
                   |}
                   |</script>
                   |<div style="position:absolute;top:0px;z-index:-50;"><input type="image" name="hBgQaaQcMmhtKLhU" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/blank.gif" value="" style="cursor: pointer;height: 0.0833333333em;width: 0.0833333333em;" title="" alt="" /><input type="text" name="opeiqRltzonDvnoN" value="0" class="invisible" id="opeiqRltzonDvnoN" /><input type="text" name="gDkWekTQDrbXGEps" value="0" class="invisible" id="gDkWekTQDrbXGEps" /></div>
                   |
                   |        <!--****************** H A U P T - F O R M U L A R -->
                   |        <a name="formular"></a><h3 class="invisible">Berliner Sparkasse (10050000) - Depotaufstellung&nbsp;Formular</h3>
                   |
                   |        <div class="osppformgrund">
                   |            <div class="labelcol"><label class="osppformbez" for="uSLAYuGlFKnluYJp">D<samp></samp>ep<code></code>ot-<dfn></dfn>Nr.<em></em><em>*</em>:</label></div><select name="uSLAYuGlFKnluYJp" class="osppformfeldmuss" id="uSLAYuGlFKnluYJp"><option value="6306069320" selected="selected">6306069320 - Ittermann, Frank</option>
                   |<option value="" disabled="true"></option></select>
                   |            <input type="image" name="LaulxdlBqQNKjKhN" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_refresh.gif" value="Aktualisieren" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfo" title="Aktualisieren" alt="Aktualisieren" />
                   |
                   |
                   | <br class="newline"/>
                   |        <div style="float: right; width: 29%">
                   |          <div style="width: 100%; text-align: left; margin-left:0; padding-left:0em;" class="col">Depotdaten exportieren<em>**</em>:</div>
                   |          <br class="newline" />
                   |          <select name="LeAmCybgcMnULqHL" class="osppformfeld" id="LeAmCybgcMnULqHL"><option value="CSV">CSV-Format</option>
                   |<option value="EXCEL">Excel-Format</option></select>
                   |          <input type="image" name="McCXzTGtconuKyAL" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_diskette.gif" value="Export" onclick="doNoCheck();" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfo" title="Export" alt="Export" />
                   |        </div>
                   |
                   |
                   |       <div class="col">Aufstellungsdatum:</div><div style="width: 30%;" class="coltext">11.12.2013 um 18:48:52 Uhr</div>
                   |       <br class="newline"/>
                   |
                   |
                   |
                   |        <div class="col">Anzahl der Depotpositionen:</div><div style="width: 30%" class="coltext">4</div>
                   |        <br class="newline"/>
                   |
                   |    <div class="col">Gesamtwert des Depots:</div><div style="width: 30%" class="coltext">8.740,90 EUR</div>
                   |    <br class="newline"/>
                   |
                   |</div>
                   |
                   |
                   |
                   |    <div class="osppformgrund">
                   |      <div class="osppinfoinhalt">
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |      </div>
                   |    </div>
                   |
                   |
                   |
                   |
                   |
                   |<!--  wenn die Depotuebersicht angezeigt werden soll -->
                   |
                   |	<h3 class="invisible">Wertpapiere im Depot&nbsp;Tabelle</h3><table style="table-layout: fixed;" class="if5_depot_table tablegrund tablegrund2"><tr class="tablerowodd">
                   |	    <th colspan="1" class="if5_first_td tableheadlinetext" nowrap="nowrap" width="1px"> </th>
                   |	    <th colspan="1" class="if5_first_td tableheadlinetext" nowrap="nowrap" width="110px">Bezeichnung<br />ISIN <input type="image" name="JObzzQWYVVfQowOK" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_up.gif" value="auf" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="auf" alt="auf" /><input type="image" name="LPQeddZSQDYylnKQ" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_down.gif" value="ab" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="ab" alt="ab" /></th>
                   |	    <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="72px">Stück/<br />Nominale </th>
                   |
                   |	        <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="86px">Akt. Kurs*<br />Datum/Zeit<br />Handelsplatz </th>
                   |
                   |
                   |	    <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="77px">Wert<br />EUR <input type="image" name="dAWBKkZvLiirVSth" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_up.gif" value="auf" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="auf" alt="auf" /><input type="image" name="GPUJAFsfXGXOrMrE" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_down.gif" value="ab" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="ab" alt="ab" /></th>
                   |	    <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="75px">Kaufkurs<br />Kaufwert<br />G/V EUR<br />G/V % <input type="image" name="sUdBOIlRmpvLseSi" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_up.gif" value="auf" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="auf" alt="auf" /><input type="image" name="AVwcSXKUPcEnKHeQ" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_down.gif" value="ab" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="ab" alt="ab" /></th>
                   |	    <th colspan="1" class="if5_first_td tableheadlinetext" nowrap="nowrap" width="1px"> </th>
                   |	    <th colspan="1" class="if5_first_td tableheadlinetext" nowrap="nowrap" width="20%">Funktionen </th>
                   |
                   |
                   |	</tr><tr><td colspan=8  style="font-weight: bold; color:#666666;">Aktien/Aktienfonds</td></tr><tr class="tableroweven">
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"><a name='toggleExpand1848528841' style='text-decoration:none;' /></td>
                   |	    <td style="padding-left:0.8em; padding-right:0.8em;border: 1px solid #E9E9E9;" class="tabledata" width="110px">COMMERZBANK AG
                   |<br />DE000CBK1001</td>
                   |	    <td style="text-align: right;" class="tabledataNew right tabledata" width="72px">80,00<br />&nbsp;St&uuml;ck<br /></td>
                   |
                   |	        <td style="text-align: left; padding-left:0.3em;" class="tabledataNew right tabledata" width="86px">11,00 EUR<br />11.12./18:41<br />Tradegate<br /></td>
                   |
                   |
                   |	    <td style="padding-left:0.1em; padding-right:0.3em;border: 1px solid #E9E9E9;text-align: right;" class="tabledata" width="77px">880,00  </td>
                   |	    <td style="text-align: right; padding-right:0.4em;" class="tabledataNew right tabledata" width="75px"><span class="habentexttable">38,147</span><br /><span class="habentexttable">3.051,75 </span><br /><span class="solltexttable"><font color="red">-2171,75<font color="red"></span><br /><span class="negativtexttable"><font color="red">-71,16</font></span><br /></td>
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"> <input type="image" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_pfeil_stark_gefallen.gif" value="Tendenz: stark gefallen" disabled="disabled" style="cursor: default;height: 0.8333333330000001em;width: 0.9166666663em;" class="feldinfoTable" title="Tendenz: stark gefallen" alt="Tendenz: stark gefallen" /></td>
                   |	    <td style="padding-right: 0.5em;" class="tabledataNew right tabledata" width="20%"> <input type="image" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /> <a href="https://web.s-investor.de/app/index.jsp?INST_ID=0004093&ident=1&modul=detail/chart.jsp&modulparam=isin=DE000CBK1001" target="target"><img src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_chart.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="B&ouml;rseninformationen" alt="B&ouml;rseninformationen" /></a> <input type="image" name="esNigQnWhgXLoPOb" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_neartime.gif" value="Realtime-Kursabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Realtime-Kursabfrage" alt="Realtime-Kursabfrage" /> <input type="image" name="yMKSANAaLYJezjFz" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_minus_rot.gif" value="Wertpapier-Verkauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Verkauf" alt="Wertpapier-Verkauf" /> <input type="image" name="EPJZnJkaaeULzjaG" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_plus_rot.gif" value="Wertpapier-Kauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Kauf" alt="Wertpapier-Kauf" /> <input type="image" name="haWfBBXMPVBCybFT" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Info" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Info" alt="Info" /></td>
                   |
                   |
                   |	</tr><tr class="tableroweven">
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"><a name='toggleExpand1848528842' style='text-decoration:none;' /></td>
                   |	    <td style="padding-left:0.8em; padding-right:0.8em;border: 1px solid #E9E9E9;" class="tabledata" width="110px">E.ON SE NA
                   |<br />DE000ENAG999</td>
                   |	    <td style="text-align: right;" class="tabledataNew right tabledata" width="72px">210,00<br />&nbsp;St&uuml;ck<br /></td>
                   |
                   |	        <td style="text-align: left; padding-left:0.3em;" class="tabledataNew right tabledata" width="86px">13,251 EUR<br />11.12./18:47<br />Tradegate<br /></td>
                   |
                   |
                   |	    <td style="padding-left:0.1em; padding-right:0.3em;border: 1px solid #E9E9E9;text-align: right;" class="tabledata" width="77px">2.782,71  </td>
                   |	    <td style="text-align: right; padding-right:0.4em;" class="tabledataNew right tabledata" width="75px"><span class="habentexttable">24,469</span><br /><span class="habentexttable">5.138,41 </span><br /><span class="solltexttable"><font color="red">-2355,70<font color="red"></span><br /><span class="negativtexttable"><font color="red">-45,84</font></span><br /></td>
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"> <input type="image" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_pfeil_stark_gefallen.gif" value="Tendenz: stark gefallen" disabled="disabled" style="cursor: default;height: 0.8333333330000001em;width: 0.9166666663em;" class="feldinfoTable" title="Tendenz: stark gefallen" alt="Tendenz: stark gefallen" /></td>
                   |	    <td style="padding-right: 0.5em;" class="tabledataNew right tabledata" width="20%"> <input type="image" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /> <a href="https://web.s-investor.de/app/index.jsp?INST_ID=0004093&ident=1&modul=detail/chart.jsp&modulparam=isin=DE000ENAG999" target="target"><img src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_chart.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="B&ouml;rseninformationen" alt="B&ouml;rseninformationen" /></a> <input type="image" name="CMvdwDPUfRAWaWvu" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_neartime.gif" value="Realtime-Kursabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Realtime-Kursabfrage" alt="Realtime-Kursabfrage" /> <input type="image" name="xwOGNxjHpdbYsHVD" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_minus_rot.gif" value="Wertpapier-Verkauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Verkauf" alt="Wertpapier-Verkauf" /> <input type="image" name="YzhrkDWTqHMcFLID" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_plus_rot.gif" value="Wertpapier-Kauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Kauf" alt="Wertpapier-Kauf" /> <input type="image" name="yAtORvafeppqvRQx" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Info" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Info" alt="Info" /></td>
                   |
                   |
                   |	</tr><tr class="tableroweven">
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"><a name='toggleExpand1848528843' style='text-decoration:none;' /></td>
                   |	    <td style="padding-left:0.8em; padding-right:0.8em;border: 1px solid #E9E9E9;" class="tabledata" width="110px">SOLARWORLD AG O.N.
                   |<br />DE0005108401</td>
                   |	    <td style="text-align: right;" class="tabledataNew right tabledata" width="72px">90,00<br />&nbsp;St&uuml;ck<br /></td>
                   |
                   |	        <td style="text-align: left; padding-left:0.3em;" class="tabledataNew right tabledata" width="86px">0,551 EUR<br />11.12./18:48<br />Tradegate<br /></td>
                   |
                   |
                   |	    <td style="padding-left:0.1em; padding-right:0.3em;border: 1px solid #E9E9E9;text-align: right;" class="tabledata" width="77px">49,59  </td>
                   |	    <td style="text-align: right; padding-right:0.4em;" class="tabledataNew right tabledata" width="75px"><span class="habentexttable">16,412</span><br /><span class="habentexttable">1.477,04 </span><br /><span class="solltexttable"><font color="red">-1427,45<font color="red"></span><br /><span class="negativtexttable"><font color="red">-96,64</font></span><br /></td>
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"> <input type="image" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_pfeil_stark_gefallen.gif" value="Tendenz: stark gefallen" disabled="disabled" style="cursor: default;height: 0.8333333330000001em;width: 0.9166666663em;" class="feldinfoTable" title="Tendenz: stark gefallen" alt="Tendenz: stark gefallen" /></td>
                   |	    <td style="padding-right: 0.5em;" class="tabledataNew right tabledata" width="20%"> <input type="image" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /> <a href="https://web.s-investor.de/app/index.jsp?INST_ID=0004093&ident=1&modul=detail/chart.jsp&modulparam=isin=DE0005108401" target="target"><img src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_chart.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="B&ouml;rseninformationen" alt="B&ouml;rseninformationen" /></a> <input type="image" name="FugMquWpslqUnDgJ" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_neartime.gif" value="Realtime-Kursabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Realtime-Kursabfrage" alt="Realtime-Kursabfrage" /> <input type="image" name="BhtEVkMMlTWKErwY" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_minus_rot.gif" value="Wertpapier-Verkauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Verkauf" alt="Wertpapier-Verkauf" /> <input type="image" name="MDgjlvFVUDmraGLz" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_plus_rot.gif" value="Wertpapier-Kauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Kauf" alt="Wertpapier-Kauf" /> <input type="image" name="FyZjwVLjwQIFKlLX" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Info" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Info" alt="Info" /></td>
                   |
                   |
                   |	</tr><tr><td colspan=8  style="font-weight: bold; color:#666666;">Dach- /Mischfonds</td></tr><tr class="tableroweven">
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"><a name='toggleExpand1848528840' style='text-decoration:none;' /></td>
                   |	    <td style="padding-left:0.8em; padding-right:0.8em;border: 1px solid #E9E9E9;" class="tabledata" width="110px">LBB-<br />PRIVATDEPOT 3 (B)
                   |<br />DE000A1JSHG1</td>
                   |	    <td style="text-align: right;" class="tabledataNew right tabledata" width="72px">170,00<br />&nbsp;St&uuml;ck<br /></td>
                   |
                   |	        <td style="text-align: left; padding-left:0.3em;" class="tabledataNew right tabledata" width="86px">29,58 EUR<br />10.12.<br />Ausserb&ouml;rslich<br /></td>
                   |
                   |
                   |	    <td style="padding-left:0.1em; padding-right:0.3em;border: 1px solid #E9E9E9;text-align: right;" class="tabledata" width="77px">5.028,60  </td>
                   |	    <td style="text-align: right; padding-right:0.4em;" class="tabledataNew right tabledata" width="75px"><span class="habentexttable">29,66</span><br /><span class="habentexttable">5.042,20 </span><br /><span class="solltexttable"><font color="red">-13,60<font color="red"></span><br /><span class="negativtexttable"><font color="red">-0,27</font></span><br /></td>
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"> <input type="image" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_pfeil_gefallen.gif" value="Tendenz: gefallen" disabled="disabled" style="cursor: default;height: 0.8333333330000001em;width: 0.9166666663em;" class="feldinfoTable" title="Tendenz: gefallen" alt="Tendenz: gefallen" /></td>
                   |	    <td style="padding-right: 0.5em;" class="tabledataNew right tabledata" width="20%"> <input type="image" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /> <a href="https://web.s-investor.de/app/index.jsp?INST_ID=0004093&ident=1&modul=detail/chart.jsp&modulparam=isin=DE000A1JSHG1" target="target"><img src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_chart.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="B&ouml;rseninformationen" alt="B&ouml;rseninformationen" /></a> <input type="image" name="XiKcCFiDJWWucuDA" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_neartime.gif" value="Realtime-Kursabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Realtime-Kursabfrage" alt="Realtime-Kursabfrage" /> <input type="image" name="iEuuXENJhGKOOawt" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_minus_rot.gif" value="Wertpapier-Verkauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Verkauf" alt="Wertpapier-Verkauf" /> <input type="image" name="wnrxhcVsrGOwmjXM" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_i_plus_rot.gif" value="Wertpapier-Kauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Kauf" alt="Wertpapier-Kauf" /> <input type="image" name="NclBNXcUbUFJshvI" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Info" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Info" alt="Info" /></td>
                   |
                   |
                   |	</tr></table>
                   |
                   |	<!-- TabelenFusszeile bauen -->
                   |
                   |		<h3 class="invisible">&nbsp;Tabelle</h3><table class="if5_depot_table tablegrund tablegrund2"><tr class="tablerowodd">
                   |            <th colspan="1" class="if5_first_td tableheadlinetext" nowrap="nowrap" width="321px"> </th>
                   |            <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="72px"> </th>
                   |
                   |            <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="70px"> </th>
                   |		    <th colspan="1" class="if5_last_td tableheadlinetext" nowrap="nowrap" width="140px"> </th>
                   |		</tr><tr class="tablerowodd">
                   |            <td style="font-weight: bold; padding-left: 0.4em;" class="tabledata tabledataNew left tabledata" width="321px">Gesamtwert des Depots</td>
                   |            <td style="text-align: right; padding-right: 0.5em; padding-left: 0.3em;" class="tabledata tabledataNew left tabledata" width="72px">8.740,90</td>
                   |
                   |            <td style="text-align: right; padding-right: 0.3em; padding-left: 0.3em;" class="tabledata tabledataNew left tabledata" width="70px"><span class="habentexttable">14.709,40 </span><br /><span class="solltexttable"><font color="red">-5968,50<font color="red"></span><br /><span class="negativtexttable"><font color="red">-40,58</font></span><br /></td>
                   |		    <td class="tabledata tabledataNew left tabledata" width="140px"></td>
                   |		</tr></table>
                   |
                   |
                   |
                   |<!--  wenn die DepotuebersichtVerkauf angezeigt werden soll -->
                   |
                   |
                   |
                   |
                   |
                   |<!--****************** Z U S A T Z I N F O -->
                   |
                   |<div class="osppformgrund">
                   |  <div class="osppinfoinhalt">* Alle Salden- und Kursangaben ohne Obligo.</div>
                   |</div>
                   |
                   |
                   |      <div class="osppformgrund">
                   |        <div class="osppinfoinhalt">
                   |          <span><em>**</em> Bitte beachten Sie unsere <a class="pfeilLink" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9zaWNoZXJoZWl0L3NpY2hlcmhlaXQtaW0taW50ZXJuZXQvc2ljaGVyaGVpdHN0aXBwcy9pbmRleC5waHA=" target="_blank" title="Neues Fenster: Sicherheitshinweise">Sicherheitshinweise</a> zum sicheren Umgang mit Download-Dateien.<br/></span>
                   |        </div>
                   |      </div>
                   |
                   |
                   |
                   |
                   |<!--****************** B U T T O N - B E R E I C H -->
                   |    <a name="pagebuttons" title="Buttonzeile" accesskey="5"></a> <div class="osppbuttonbereich"><h3 class="invisible">Buttonzeile</h3><a href="https://www.berliner-sparkasse.de/module/glossar/glossar_popup.php?words=Brokerage%20/%20Depotanzeige&complete=true" target="_blank"><img src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_symbol_hilfe_rot.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="osppbuttonlink if5_hilfe if5_hilfe_finanz" title="Neues Fenster:Hilfe Depotanzeige" alt="Neues Fenster:Hilfe Depotanzeige" /></a><a href="https://banking.berliner-sparkasse.de/portal/print/?nc=1&pw=sxsKb33DtMf2X2y&w=PORTFOLIO_SECURITIES_PRINT&r=bc790d5830e9604a" target="_blank"><img src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_b_Druckan.png" style="cursor: pointer;height: 2.2499999991000004em;width: 10.499999995800001em;" class="osppbuttonlink" title="Neues Fenster:Druckansicht" alt="Neues Fenster:Druckansicht" /></a><input type="image" name="hBgQaaQcMmhtKLhU" src="/ifdata/10050000/IPSTANDARD/3/content/www/pixel/ipo/if5_b_aktualisieren.png" value="Aktualisieren" accesskey="a" style="cursor: pointer;height: 2.2499999991000004em;width: 10.4166666625em;" class="osppbuttonlinklast" title="Aktualisieren" alt="Aktualisieren" /></div>
                   |
                   |      <body onload="jumpToAnchor('null')">
                   |
                   |     </body>
                   |
                   |    <input type="hidden" name="DrBdRdElhtwAOqSi" value="2671b88042c49169" /><input type="hidden" name="oRoxmmHyjFoVtfiz" value="d0aeb271c55cf778" /></form>
                   |<script type="text/javascript">
                   |
                   |var focusControl = document.forms['698763a53994d83f'].elements['dHXgxnkXiljqDvoi'];
                   |
                   |if (focusControl) {
                   |  if (focusControl.type != 'hidden' && !focusControl.disabled) {
                   |     focusControl.focus();
                   |  }
                   |}
                   |</script>
                   |
                   |
                   |</div><!-- ContentContainerMainLayout zu -->
                   |
                   |        <div class="if5_verlauf_u">&nbsp;</div>
                   |        </div>
                   |    <div class="if5_white_u">&nbsp;</div>
                   |    </div>
                   |</div>
                   |
                   |
                   |		    			    			    	    <div class="if5_gvContentElement if5_gvContElemBottom"><div class="p">Das B&ouml;rsenCenter bildet die Basis f&uuml;r fundierte Entscheidungen. Hier holen Sie sich alle Infos zu Ihren Wertpapieren oder ermitteln die ISIN/WKN eines gew&uuml;nschten Wertpapiers.<ul><li class="l1"><a title="Zum BörsenCenter" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi93ZXJ0cGFwaWVyZV9ib2Vyc2VuaW5mb3MvYm9lcnNlbmNlbnRlci9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkZ3ZXJ0cGFwaWVyZV9ib2Vyc2VuaW5mb3MlMkZib2Vyc2VuY2VudGVyJTJG"   target="_top">Zum B&ouml;rsenCenter</a></li></ul></div></div>
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |		    			    			    	</div>
                   | <div class="if5_content_footer if5_Druckansichtsbuttonseite">
                   |<div class="if5_service c5_2 banking"><ul class="service_list"><li class="seite_senden" id="empfehlen"><a class="l3b c0" href="https://banking.berliner-sparkasse.de/portal/portal/_o:33d4ef7be9088a84/?r=9ec045483025a109&amp;p=p.finanzstatus&amp;n=%2Fonlinebanking%2Ffinanzstatus%2F" id="empfehlen_link">Finanzstatus</a></li><li class="seite_drucken c0 druck_ohne_js" id="drucken"><script type="text/javascript">
                   |  <!--
                   |  document.write('<a class="l3b c0" href="javascript:window.print();">Seite drucken<span class="druck_ohne_js" id="druckwrap"><span class="if5_white_o">&nbsp;&nbsp;</span><span class="if5_rand"><span class="if5_verlauf_o">&nbsp;&nbsp;</span><span class="hw_druck_ohne_js">F&uuml;r diese Funktion muss JavaScript aktiviert sein.</span><span class="if5_verlauf_u">&nbsp;&nbsp;</span></span><span class="if5_white_u">&nbsp;&nbsp;</span></span></a>');
                   |  //-->
                   |  </script>
                   |<span style="display:none"><script type="text/javascript" language="JavaScript">/*<!--*/ document.getElementById('druckwrap').className = 'druck_mit_js';document.getElementById('drucken').className = 'seite_drucken c0'; //--></script></span></li><li class="seite_anfang c0"><a class="l3b c0" href="#">Seitenanfang</a></li></ul></div>
                   |
                   |
                   |		    			    			    	   	<div class="if5_footer"><ul class="footer_list l4"><li class="footer_listitem"><a title="Mobile Version" href="http://m.berliner-sparkasse.de"   target="_blank">Mobile Version</a></li><li class="footer_listitem"><a title="BIC: BELADEBE" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvaW1wcmVzc3VtL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRmltcHJlc3N1bSUyRg=="   target="_top">BIC: BELADEBE</a></li><li class="footer_listitem"><a title="Impressum" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvaW1wcmVzc3VtL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRmltcHJlc3N1bSUyRg=="   target="_top">Impressum</a></li><li class="footer_listitem"><a title="AGB" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvYWdiL2FsbGdlbWVpbmVfZ2VzY2hhZWZ0c2JlZGluZ3VuZ2VuL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRmFnYiUyRmFsbGdlbWVpbmVfZ2VzY2hhZWZ0c2JlZGluZ3VuZ2VuJTJG"   target="_top">AGB</a></li><li class="footer_listitem"><a title="Datenschutz" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvZGF0ZW5zY2h1dHovaW5kZXgucGhwP249JTJGbW9kdWxlJTJGc3RhdGljJTJGZGF0ZW5zY2h1dHolMkY="   target="_top">Datenschutz</a></li><li class="footer_listitem"><a title="Preise und Hinweise" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvcHJlaXNlX2hpbndlaXNlL3ZvcnRlaWxlL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRnByZWlzZV9oaW53ZWlzZSUyRnZvcnRlaWxlJTJG"   target="_top">Preise und Hinweise</a></li><li class="footer_listitem"><a title="Sitemap" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zaXRlbWFwL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnNpdGVtYXAlMkY="   target="_top">Sitemap</a></li></ul></div>
                   |
                   |</div>
                   |<!--Footerzu-->
                   |</div>
                   |<!--Content zu-->
                   |
                   |		    			    			    	<div class="if5_containerwrapper">
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |<div class="if5_container if5_infobox">
                   |  <div class="rounded">
                   |    <div class="if5_white_o_rounded">
                   |      &nbsp;
                   |    </div>
                   |    <div class="if5_rand">
                   |      <div class="if5_verlauf_o">
                   |        &nbsp;
                   |      </div>
                   |      <div class="rounded_content">
                   |        <h4 class="info">
                   |            Info-Box
                   |        </h4>
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |            <p id="sdauer"></p>
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |                <div id="direkteinstieg" class="favoriten close">
                   |
                   |                    <p class="info_favoriten">
                   |                        <a href="/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19ndi0xMzA1NTkwODg5RnJhZ21lbnQyN3xlQUNUSU9OPTE9VE9HR0xFX0RFVEFJTF9GQVY_/_o:33d4ef7be9088a84/"> Favoriten</a>
                   |                    </p>
                   |                </div>
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |      </div>
                   |      <div class="if5_verlauf_u">
                   |        &nbsp;
                   |      </div>
                   |    </div>
                   |    <!-- NI: Verlauf hinzugefügt -->
                   |  </div>
                   |  <!-- NI: Rand -->
                   |  <div class="if5_white_u_rounded">
                   |    &nbsp;
                   |  </div>
                   |  <!-- NI: runde Ecke im Verlauf -->
                   |<!-- NI: Ende umschliessendes DIV -->
                   |</div>
                   |
                   |<!--
                   |ticktack
                   | --><script language="JavaScript" type="text/javascript"><!--
                   |var ticktack=12.0*60*1000+new Date().getTime();tick();function tick() {var timeoutID;var s;var t=ticktack-new Date().getTime();t=(t<0)?0:Math.round(t/1000);s=""+ ((t>60)?Math.ceil(t/60).toFixed(0)+" Min.":t.toFixed(0)+" Sek.");document.getElementById("sdauer").innerHTML=" Zu Ihrer Sicherheit erfolgt die automatische Abmeldung in "+s;if (t!=0) timeoutID = window.setTimeout("tick()",(t>60) ? 6000 : 1000);else {window.clearTimeout(timeoutID);location.replace("/portal/portal/_o:33d4ef7be9088a84/LogoutTimeout");}}
                   | --></script>
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |		    			    			    	    <div class="if5_container if5_werbung" style=""><div class="rounded"><div class="if5_white_o_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --><div class="if5_rand"><div class="if5_verlauf_o">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --><!--  Backgroundimage wird per Inline-Style gesetzt, da Aktionsfl&auml;che   --><div class="rounded_content"><h4><span>Tagesgeldkonto Direkt</span></h4><div class="clip"><img src="/ifdata/10050000/IPSTANDARD/3/content/www/pool/contentelements/container/tagesgeldkonto_direkt_x80N1o/bg_gen.jpg" /></div><div class="textsection" style=""><h3><span></span></h3><p>Die ideale Erg&auml;nzung zum Girokonto - einfach &amp; bequem Geld anlegen oder monatlich sparen.</p><div class="if5_container_link"><div><a title="Jetzt eröffnen" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9zcGFyZW5fYW5sZWdlbi90YWdlc2dlbGQvdm9ydGVpbGUvaW5kZXgucGhwP249JTJGcHJpdmF0a3VuZGVuJTJGc3BhcmVuX2FubGVnZW4lMkZ0YWdlc2dlbGQlMkZ2b3J0ZWlsZSUyRg=="   target="_top">Jetzt er&ouml;ffnen</a></div></div></div></div><div class="if5_verlauf_u">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --></div><!--  NI: Rand  --><div class="if5_white_u_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --></div><!--  NI: Ende umschliessendes DIV  --></div>
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |
                   |</div>
                   |
                   |
                   |		    			    		    			    		  		  				<br />
                   |	</div>
                   |	</div>
                   |	    <!-- Slot slot.peel (leer) -->
                   |<br style="clear:both;"/>
                   |  <form name='portletform' action="" method="post">
                   |    <input type='hidden' name='portlets'/>
                   |    <input type='hidden' name='page' value='org.apache.jetspeed.om.page.impl.ContentPageImpl@a685d28a'/>
                   |  </form>
                   |
                   |</body>
                   |</html>
                   |
                   |
                 """.stripMargin
      val data = new SparKassenClient().parseStockOverview(html)
      println(data)
      data.accounts.get("COMMERZBANK AG").get.value must beEqualTo(880.00)
    }
  }
}
