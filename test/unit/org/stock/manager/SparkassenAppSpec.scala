package unit.org.stock.manager

import org.specs2.mutable.Specification
import unit.org.stock.manager.test.Betamax
import co.freeside.betamax.{MatchRule, TapeMode}
import java.util.Comparator
import co.freeside.betamax.message.Request
import tools.imports.{User, SparKassenClient}

class SparkassenAppSpec extends Specification {
  sys.props.+=("com.ning.http.client.AsyncHttpClientConfig.useProxyProperties" -> "true")
  //activate betamax proxy for dispatch
  sequential
  isolated //to have seperated hsql databases

  "The SparkassenApp" should {
    "given system properties login data return correct User case class" in {
      //TODO not working only working with define getOrElse
      sys.props.+=("sparkasse_username" -> "username")
      sys.props.+=("sparkasse_password" -> "password")
      val user = User.fromProperties
      user.username must beEqualTo("username")
      user.password must beEqualTo("password")
    }
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
                   |              <link rel="stylesheet" href="/css/opttan4_0_3.css" type="text/css" />
                   |
                   |			<link rel="shortcut icon" type="image/x-icon" href="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/favicon.ico" />
                   |        <link rel="icon" type="image/x-icon" href="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/favicon.ico" />
                   |	<title>Berliner Sparkasse (10050000) - Depotaufstellung</title>
                   |	<meta name="keywords" content="Brokerage,Depotaufstellung,Depotübersicht" />
                   |	<meta name="description" content="Die Internet-Filiale der Berliner Sparkasse: Online-Banking, Rente, Aktien, Baufinanzierung, ImmobilienangeboteDie Internet-Filiale der Berliner Sparkasse: Online-Banking, Rente, Aktien, Baufinanzierung, Immobilienangebote" />
                   |			  	<META HTTP-EQUIV="Refresh" CONTENT="720; URL=/portal/portal/_o:5ead6b7e018d10d9/LogoutTimeout" />
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
                   |		    			    			    	    <div class="if5_gsw b5" style="background: url(/ifdata/10050000/IPSTANDARD/4/content/www/pool/contentelements/gsw/autokredit_vbd_KvCRop/op_gen.jpg) no-repeat scroll right top;"><div class="gsw_text"><h2 class="gsw_h1"><a title="Berechnen sie sich jetzt ihren Autokredit" href="https://service.berliner-sparkasse.de/msgen/autokredit/der-s-autokredit/"   target="_blank">Starten Sie jetzt mobil ins Fr&uuml;hjahr mit dem<br />fairen Sparkassen-Autokredit!</a></h2><div class="gsw_weiter_link"><p class="l2 weiter_linktext"><a title="Berechnen sie sich jetzt ihren Autokredit" class="c1" href="https://service.berliner-sparkasse.de/msgen/autokredit/der-s-autokredit/"   target="_blank">Jetzt informieren</a></p></div></div></div>
                   |
                   |</div>
                   |
                   |
                   |		    			    			    		<div class="if5_metanavi">
                   |    	<div class="metanavi_blz"><p class="blz"> BLZ 10050000 | BIC BELADEBEXXX</p></div>
                   |
                   |
                   |
                   |		    			    			    	    <div class="metanavi_navi"><ul class="metanavi_list"><li class="metanavi_listitem"><a title="Über uns" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9paHJlX3NwYXJrYXNzZS9pbmRleC5waHA/bj0lMkZtb2R1bGUlMkZpaHJlX3NwYXJrYXNzZSUyRiZhbXA7Y3RwPWViNGZjM2FiMWE5MTcyMjE4YzIyYWU2MTU3NTc4OTYy"   target="_top">&Uuml;ber uns</a></li><li class="metanavi_listitem"><a title="Standorte der Berliner Sparkasse" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9paHJlX3NwYXJrYXNzZS9zdGFuZG9ydGUtZGVyLWJlcmxpbmVyLXNwYXJrYXNzZS9kZXRhaWxzL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRmlocmVfc3Bhcmthc3NlJTJGc3RhbmRvcnRlLWRlci1iZXJsaW5lci1zcGFya2Fzc2UlMkZkZXRhaWxzJTJGJmFtcDtjdHA9MDZmYmUwYmFlZmVkZTRhNmZmZWM0MmRhNGI5OWIzOTQ="   target="_top">Standorte</a></li><li class="metanavi_listitem"><a title="Kontakt" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9rb250YWt0L2tvbnRha3R3ZWljaGUvaW5kZXgucGhwP249JTJGbW9kdWxlJTJGa29udGFrdCUyRktvbnRha3R3ZWljaGUlMkYmYW1wO2N0cD02M2FiYmJmZDI2OTE5NTA2MWE2NTNjMGZiMDEzZTVhMg=="   target="_top">Kontakt</a></li><li class="metanavi_listitem"><a title="Karriere" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2thcnJpZXJlL2luZGV4LnBocD9uPSUyRmthcnJpZXJlJTJGJmFtcDtjdHA9N2NjYmI1MTFiNTM4YTQyYjI5YWZiZDA0ODY2N2E0OTA="   target="_top">Karriere</a></li><li class="metanavi_listitem"><a title="Shop" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9iYW5raW5nL3NwYXJrYXNzZW5zaG9wL2ltLXNwYXJrYXNzZW5zaG9wLWZpbmRlbi1zaWUvaW5kZXgucGhwP249JTJGcHJpdmF0a3VuZGVuJTJGYmFua2luZyUyRnNwYXJrYXNzZW5zaG9wJTJGaW0tc3Bhcmthc3NlbnNob3AtZmluZGVuLXNpZSUyRiZhbXA7Y3RwPTYyMTNhMDU3YWYyN2JjZmQ4MGUzOTdhZjhmOGMwNzBm"   target="_top">Shop</a></li><li class="metanavi_listitem"><a title="Videos" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9tZWRpYWNlbnRlci91ZWJlcnNpY2h0L2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRm1lZGlhY2VudGVyJTJGdWViZXJzaWNodCUyRiZhbXA7Y3RwPWY3YjcxNDE0MDQ2OGZmMGQ5YWRlMWVmOGZiZmVjODAz"   target="_top">Videos</a></li></ul><div class="fontsize" id="metanaviloggedinfontsize" style="display:none"><ul style="background:no-repeat;"><li _size="62.5" _stat="statnormal.png" title="Schrift normal"><img alt="Schrift normal" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/fs0.png" /></li><li _size="68.8" _stat="statgroesser.png" title="Schrift größer"><img alt="Schrift größer" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/fs1.png" /></li><li _size="75" _stat="statgross.png" title="Schrift groß"><img alt="Schrift groß" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/fs2.png" /></li></ul></div><script type="text/javascript" language="JavaScript" src="/ifdata/10050000/IPSTANDARD/4/content/www/js/fontsize.js"><!-- --></script><script language="JavaScript" type="text/javascript">try {new FontSizeControl('metanaviloggedinfontsize',{mode:3});} catch(e){}</script></div>
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
                   |    	<span style='display:none;'>ausgew&auml;hlt: Online-Banking: Brokerage: Depotanzeige</span><ul class='nav0wrap'><li class='nav0item open active withsub'><h3><a target='_top' title='Online-Banking' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.finanzstatus&amp;n=%2Fonlinebanking%2Ffinanzstatus%2F'>Online-Banking</a></h3><form action='/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19UcmVlTmF2aWdhdGlvblBvcnRsZXR8Zg__/_o:5ead6b7e018d10d9/?RJOQWOdIhJFFMxNz.x=1' class='tx_anmelden' method='post' accept-charset='UTF-8' autocomplete='off' onsubmit="javascript:if(this.id !== 'clicked') {this.id='clicked';return true;} else {window.alert('Ihre Daten wurden bereits abgesendet!');return false;};"><fieldset><p> Frank Ittermann</p><input type='image' title='Sicher abmelden' alt='Abmelden' name='anmeldebutton' src='/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/if5_abmelden.png'/></fieldset></form><form action='/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19UcmVlTmF2aWdhdGlvblBvcnRsZXR8Zg__/_o:5ead6b7e018d10d9/' class='tx_anmelden' method='post' accept-charset='UTF-8' autocomplete='off' onsubmit="javascript:if(this.id !== 'clicked') {this.id='clicked';return true;} else {window.alert('Ihre Daten wurden bereits abgesendet!');return false;};"><fieldset><input type='hidden' id='direkt' name='direkt' value='true'/><div class='direkteinstieg'><label for='pageid'>direkt zu:</label><select id='pageid' name='p'><option value='/onlinebanking/finanzstatus/' selected='selected'>Finanzstatus</option><option value='/onlinebanking/umsaetze/umsatzabfrage_neu/'>Ums&auml;tze</option><option value='/onlinebanking/banking/ueberweisung/sepa_einzelueberweisung/'>&Uuml;berweisung</option><option value='/onlinebanking/banking/komfortueberweisung/'>&Uuml;bertrag</option><option value='/onlinebanking/banking/dauerauftrag_neu/'>Dauerauftrag</option><option value='/onlinebanking/banking/handy_laden/'>Handy laden</option></select><input type='image' title='Direkt zu...' alt='Weiter' name='direktZuButton' src='/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/primlink_navi.gif'/></div></fieldset></form><ul class='nav1wrap'><li class='nav1item'><h4><a target='_top' title='Finanzstatus' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.finanzstatus&amp;n=%2Fonlinebanking%2Ffinanzstatus%2F'>Finanzstatus</a></h4></li><li class='nav1item'><h4><a target='_top' title='Ums&auml;tze' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.umsatzabfrage_neuyhaZcp&amp;n=%2Fonlinebanking%2Fumsaetze%2Fumsatzabfrage_neu%2F'>Ums&auml;tze</a></h4></li><li class='nav1item '><h4><a target='_top' title='Kontoauszug' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.kontoauszug_epostfach92fOop&amp;n=%2Fonlinebanking%2Fkontoauszug_epostfach%2F'>Kontoauszug</a></h4></li><li class='nav1item'><h4><a target='_top' title='Banking' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.sepa_einzelueberweisung&amp;n=%2Fonlinebanking%2Fbanking%2Fueberweisung%2Fsepa_einzelueberweisung%2F'>Banking</a></h4></li><li class='nav1item active withsub'><h4><a target='_top' title='Brokerage' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.depotaufstellung&amp;n=%2Fonlinebanking%2Fbrokerage%2Fdepot_uebersicht%2Fdepotaufstellung%2F'>Brokerage</a></h4><ul class='nav2wrap'><li class='nav2item active current'><h5><a target='_top' title='Depotanzeige (ausgew&auml;hlt)' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.depotaufstellung&amp;n=%2Fonlinebanking%2Fbrokerage%2Fdepot_uebersicht%2Fdepotaufstellung%2F'>Depotanzeige <span style='display:none;'>(ausgew&auml;hlt)</span></a></h5></li><li class='nav2item'><h5><a target='_top' title='Orderbuch' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.orderstatus&amp;n=%2Fonlinebanking%2Fbrokerage%2Forderbuch%2F'>Orderbuch</a></h5></li><li class='nav2item'><h5><a target='_top' title='Kauf' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.neukauf&amp;n=%2Fonlinebanking%2Fbrokerage%2Fneukauf%2F'>Kauf</a></h5></li><li class='nav2item'><h5><a target='_top' title='Verkauf' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.wertpapier_verkauf&amp;n=%2Fonlinebanking%2Fbrokerage%2Fwertpapier_verkauf%2F'>Verkauf</a></h5></li><li class='nav2item'><h5><a target='_top' title='Neuemission' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.neuemissionen&amp;n=%2Fonlinebanking%2Fbrokerage%2Fneuemissionen%2F'>Neuemission</a></h5></li><li class='nav2item'><h5><a target='_blank' title='B&ouml;rsenCenter' href='https://web.s-investor.de/app/markt.htm?INST_ID=0004093'>B&ouml;rsenCenter</a></h5></li></ul></li><li class='nav1item'><h4><a target='_top' title='Kreditkarte' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.kreditkartendetails&amp;n=%2Fonlinebanking%2Fkreditkarte%2Fkartendetails%2F'>Kreditkarte</a></h4></li><li class='nav1item'><h4><a target='_top' title='Elektronisches Postfach' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.postfachbestand&amp;n=%2Fonlinebanking%2Felektr_postfach%2Fpostfachbestand%2F'>Elektronisches Postfach</a></h4></li><li class='nav1item'><h4><a target='_top' title='Offene Auftr&auml;ge' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.terminauftraege&amp;n=%2Fonlinebanking%2Foffene_auftraege%2F3ebene%2Fterminauftraege%2F'>Offene Auftr&auml;ge</a></h4></li><li class='nav1item'><h4><a target='_top' title='Sicherheit' href='/portal/portal/_o:5ead6b7e018d10d9/?p=p.pin_tan_verwaltung&amp;n=%2Fonlinebanking%2Fsicherheit_angemeldet%2Fpin_tan_verwaltung%2F'>Sicherheit</a></h4></li><li class='nav1item '><h4><a target='_top' title='Aktuelles und Service' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9iYW5raW5nL2FrdHVlbGxlLWhpbndlaXNlL2FyY2hpdl8yMDE0L3VlYmVyc2ljaHQvaW5kZXgucGhwP249JTJGb25saW5lYmFua2luZyUyRnNlcnZpY2UlMkZha3R1ZWxsZXMlMkYyMDE0JTJG'>Aktuelles und Service</a></h4></li></ul></li><li class='nav0item close'><h3><a target='_top' title='Online-Produkte' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL29ubGluZS1wcm9kdWt0ZS9ob21lcGFnZS9pbmRleC5waHA/bj0lMkZvbmxpbmUtcHJvZHVrdGUlMkY='>Online-Produkte</a></h3></li><li class='nav0item close'><h3><a target='_top' title='Privatkunden' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9pbmRleC5waHA/bj0lMkZwcml2YXRrdW5kZW4lMkY='>Privatkunden</a></h3></li><li class='nav0item close'><h3><a target='_top' title='Firmenkunden' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2Zpcm1lbmt1bmRlbi9pbmRleC5waHA/bj0lMkZmaXJtZW5rdW5kZW4lMkY='>Firmenkunden</a></h3></li><li class='nav0item close '><h3><a target='_top' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL2ZpbmFuemtvbnplcHQvaW5kZXgucGhwP249JTJGc3Bhcmthc3Nlbi1maW5hbnprb256ZXB0JTJG'>Sparkassen-Finanzkonzept</a></h3></li><li class='nav0item open withsub'><h3><a target='_top' title='Spezielle Angebote' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL2p1bmdlLWxldXRlL2luZGV4LnBocD9uPSUyRnppZWxncnVwcGVuJTJGanVuZ2UtbGV1dGUlMkY='>Spezielle Angebote</a></h3><ul class='nav1wrap'><li class='nav1item'><h4><a target='_top' title='Junge Leute' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL2p1bmdlLWxldXRlL2luZGV4LnBocD9uPSUyRnppZWxncnVwcGVuJTJGanVuZ2UtbGV1dGUlMkY='>Junge Leute</a></h4></li><li class='nav1item'><h4><a target='_top' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL3ByaXZhdGUtYmFua2luZy9pbmRleC5waHA/bj0lMkZ6aWVsZ3J1cHBlbiUyRnByaXZhdGUtYmFua2luZyUyRg=='>Private Banking</a></h4></li><li class='nav1item '><h4><a target='_top' href='https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ppZWxncnVwcGVuL3MtdmVyZWluL2luZGV4LnBocD9uPSUyRnppZWxncnVwcGVuJTJGcy12ZXJlaW4lMkY='>Vereine</a></h4></li><li class='nav1item '><h4><a target='_blank' title='Mobile Beratung' href='https://service.berliner-sparkasse.de/msgen/mobile-beratung/'>Mobile Beratung</a></h4></li></ul></li></ul>
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
                   |    <form id="a63b48c32d81dc67" method="post" action="/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19ldm9HdlBvcnRsZXR8YzB8ZDF8ZV9zcGFnZT0xPS9Db250cm9sbGVyLmRv/_o:5ead6b7e018d10d9/" onsubmit="return teste();">
                   |<script type="text/javascript">
                   |var submitTime = 0;
                   |function teste() {
                   |	if (submitTime == 0) {
                   |		submitTime = new Date().getTime();
                   |	}
                   |	field = document.getElementById('PLpJHwgngJjXMFFR');
                   |	if ( field.value == '0' ) {
                   |		fieldNoCheck = document.getElementById('OCagmypbkEMYOlBG');
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
                   |	fieldNoCheck = document.getElementById('OCagmypbkEMYOlBG');
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
                   |<div style="position:absolute;top:0px;z-index:-50;"><input type="image" name="tChfFhWoPzmauLld" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" style="cursor: pointer;height: 0.0833333333em;width: 0.0833333333em;" title="" alt="" /><input type="text" name="PLpJHwgngJjXMFFR" value="0" class="invisible" id="PLpJHwgngJjXMFFR" /><input type="text" name="OCagmypbkEMYOlBG" value="0" class="invisible" id="OCagmypbkEMYOlBG" /></div>
                   |
                   |        <!--****************** H A U P T - F O R M U L A R -->
                   |        <a name="formular"></a><h3 class="invisible">Berliner Sparkasse (10050000) - Depotaufstellung&nbsp;Formular</h3>
                   |
                   |        <div class="osppformgrund">
                   |            <div class="labelcol"><label class="osppformbez" for="TZWmEznDStbclZcU">D<em></em>epo<dfn></dfn>t-<dfn></dfn>Nr<cite></cite>.<em>*</em>:</label></div><select name="TZWmEznDStbclZcU" class="osppformfeldmuss" id="TZWmEznDStbclZcU"><option value="6306069320" selected="selected">6306069320 - Ittermann, Frank</option>
                   |<option value="" disabled="true"></option></select>
                   |            <input type="image" name="IQdEquNAnWqikXTe" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_refresh.gif" value="Aktualisieren" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfo" title="Aktualisieren" alt="Aktualisieren" />
                   |
                   |
                   | <br class="newline"/>
                   |        <div style="float: right; width: 29%">
                   |          <div style="width: 100%; text-align: left; margin-left:0; padding-left:0em;" class="col">Depotdaten exportieren<em>**</em>:</div>
                   |          <br class="newline" />
                   |          <select name="YLRcKTDmIecCREYE" class="osppformfeld" id="YLRcKTDmIecCREYE"><option value="CSV">CSV-Format</option>
                   |<option value="EXCEL">Excel-Format</option></select>
                   |          <input type="image" name="jEwVUlDtIervYvmQ" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_diskette.gif" value="Export" onclick="doNoCheck();" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfo" title="Export" alt="Export" />
                   |        </div>
                   |
                   |
                   |       <div class="col">Aufstellungsdatum:</div><div style="width: 30%;" class="coltext">19.05.2014 um 14:49:52 Uhr</div>
                   |       <br class="newline"/>
                   |
                   |
                   |
                   |        <div class="col">Anzahl der Depotpositionen:</div><div style="width: 30%" class="coltext">4</div>
                   |        <br class="newline"/>
                   |
                   |    <div class="col">Gesamtwert des Depots:</div><div style="width: 30%" class="coltext">9.376,29 EUR</div>
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
                   |	    <th colspan="1" class="if5_first_td tableheadlinetext" nowrap="nowrap" width="110px">Bezeichnung<br />ISIN <input type="image" name="DooaOVlPnKGEsemp" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_up.gif" value="auf" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="auf" alt="auf" /><input type="image" name="WXaoHnmhYoxHfJOy" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_down.gif" value="ab" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="ab" alt="ab" /></th>
                   |	    <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="72px">Stück/<br />Nominale </th>
                   |
                   |	        <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="86px">Akt. Kurs*<br />Datum/Zeit<br />Handelsplatz </th>
                   |
                   |
                   |	    <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="77px">Wert<br />EUR <input type="image" name="IkYosQxnfhDHXumo" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_up.gif" value="auf" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="auf" alt="auf" /><input type="image" name="fcwsLduMNPJcIufb" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_down.gif" value="ab" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="ab" alt="ab" /></th>
                   |	    <th colspan="1" class="tableheadlinetext" nowrap="nowrap" width="75px">Kaufkurs<br />Kaufwert<br />G/V EUR<br />G/V % <input type="image" name="TGgnOrbipriPXhMN" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_up.gif" value="auf" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="auf" alt="auf" /><input type="image" name="nuocIOybcQqCNGsK" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_down.gif" value="ab" style="cursor: pointer;height: 0.5833333331em;width: 0.5833333331em;" class="osppbuttoninhalt" title="ab" alt="ab" /></th>
                   |	    <th colspan="1" class="if5_first_td tableheadlinetext" nowrap="nowrap" width="1px"> </th>
                   |	    <th colspan="1" class="if5_first_td tableheadlinetext" nowrap="nowrap" width="20%">Funktionen </th>
                   |
                   |
                   |	</tr><tr><td colspan=8  style="font-weight: bold; color:#666666;">Aktien/Aktienfonds</td></tr><tr class="tableroweven">
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"><a name='toggleExpand1449521771' style='text-decoration:none;' /></td>
                   |	    <td style="padding-left:0.8em; padding-right:0.8em;border: 1px solid #E9E9E9;" class="tabledata" width="110px">COMMERZBANK AG
                   |<br />DE000CBK1001</td>
                   |	    <td style="text-align: right;" class="tabledataNew right tabledata" width="72px">130,00<br />&nbsp;St&uuml;ck<br /></td>
                   |
                   |	        <td style="text-align: left; padding-left:0.3em;" class="tabledataNew right tabledata" width="86px">10,90 EUR<br />19.05./14:48<br />Tradegate<br /></td>
                   |
                   |
                   |	    <td style="padding-left:0.1em; padding-right:0.3em;border: 1px solid #E9E9E9;text-align: right;" class="tabledata" width="77px">1.417,00  </td>
                   |	    <td style="text-align: right; padding-right:0.4em;" class="tabledataNew right tabledata" width="75px"><span class="habentexttable">27,586</span><br /><span class="habentexttable">3.586,24 </span><br /><span class="solltexttable"><font color="red">-2169,24<font color="red"></span><br /><span class="negativtexttable"><font color="red">-60,49</font></span><br /></td>
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"> <input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_pfeil_stark_gefallen.gif" value="Tendenz: stark gefallen" disabled="disabled" style="cursor: default;height: 0.8333333330000001em;width: 0.9166666663em;" class="feldinfoTable" title="Tendenz: stark gefallen" alt="Tendenz: stark gefallen" /></td>
                   |	    <td style="padding-right: 0.5em;" class="tabledataNew right tabledata" width="20%"> <input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /> <a href="https://web.s-investor.de/app/index.jsp?INST_ID=0004093&ident=1&modul=detail/chart.jsp&modulparam=isin=DE000CBK1001" target="target"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_chart.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="B&ouml;rseninformationen" alt="B&ouml;rseninformationen" /></a> <input type="image" name="IvJlWQSVmjnYanOT" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_neartime.gif" value="Realtime-Kursabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Realtime-Kursabfrage" alt="Realtime-Kursabfrage" /> <input type="image" name="tEspZBbAuPEUrhNK" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_minus_rot.gif" value="Wertpapier-Verkauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Verkauf" alt="Wertpapier-Verkauf" /> <input type="image" name="SUvYRddnTpQPnadW" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_plus_rot.gif" value="Wertpapier-Kauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Kauf" alt="Wertpapier-Kauf" /> <input type="image" name="QLeTUfbnLEzdROsJ" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Info" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Info" alt="Info" /></td>
                   |
                   |
                   |	</tr><tr class="tableroweven">
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"><a name='toggleExpand1449521773' style='text-decoration:none;' /></td>
                   |	    <td style="padding-left:0.8em; padding-right:0.8em;border: 1px solid #E9E9E9;" class="tabledata" width="110px">E.ON SE NA
                   |<br />DE000ENAG999</td>
                   |	    <td style="text-align: right;" class="tabledataNew right tabledata" width="72px">210,00<br />&nbsp;St&uuml;ck<br /></td>
                   |
                   |	        <td style="text-align: left; padding-left:0.3em;" class="tabledataNew right tabledata" width="86px">13,219 EUR<br />19.05./14:47<br />Tradegate<br /></td>
                   |
                   |
                   |	    <td style="padding-left:0.1em; padding-right:0.3em;border: 1px solid #E9E9E9;text-align: right;" class="tabledata" width="77px">2.775,99  </td>
                   |	    <td style="text-align: right; padding-right:0.4em;" class="tabledataNew right tabledata" width="75px"><span class="habentexttable">24,469</span><br /><span class="habentexttable">5.138,41 </span><br /><span class="solltexttable"><font color="red">-2362,42<font color="red"></span><br /><span class="negativtexttable"><font color="red">-45,98</font></span><br /></td>
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"> <input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_pfeil_stark_gefallen.gif" value="Tendenz: stark gefallen" disabled="disabled" style="cursor: default;height: 0.8333333330000001em;width: 0.9166666663em;" class="feldinfoTable" title="Tendenz: stark gefallen" alt="Tendenz: stark gefallen" /></td>
                   |	    <td style="padding-right: 0.5em;" class="tabledataNew right tabledata" width="20%"> <input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /> <a href="https://web.s-investor.de/app/index.jsp?INST_ID=0004093&ident=1&modul=detail/chart.jsp&modulparam=isin=DE000ENAG999" target="target"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_chart.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="B&ouml;rseninformationen" alt="B&ouml;rseninformationen" /></a> <input type="image" name="RxLlytbFaaAGCDdA" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_neartime.gif" value="Realtime-Kursabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Realtime-Kursabfrage" alt="Realtime-Kursabfrage" /> <input type="image" name="wMeRPOyMCRRQlQBk" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_minus_rot.gif" value="Wertpapier-Verkauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Verkauf" alt="Wertpapier-Verkauf" /> <input type="image" name="tqsZjHfhIJlZTchs" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_plus_rot.gif" value="Wertpapier-Kauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Kauf" alt="Wertpapier-Kauf" /> <input type="image" name="DMSLThYnIrtufNSf" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Info" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Info" alt="Info" /></td>
                   |
                   |
                   |	</tr><tr><td colspan=8  style="font-weight: bold; color:#666666;">Dach- /Mischfonds</td></tr><tr class="tableroweven">
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"><a name='toggleExpand1449521770' style='text-decoration:none;' /></td>
                   |	    <td style="padding-left:0.8em; padding-right:0.8em;border: 1px solid #E9E9E9;" class="tabledata" width="110px">LBB-<br />PRIVATDEPOT 3 (B)
                   |<br />DE000A1JSHG1</td>
                   |	    <td style="text-align: right;" class="tabledataNew right tabledata" width="72px">170,00<br />&nbsp;St&uuml;ck<br /></td>
                   |
                   |	        <td style="text-align: left; padding-left:0.3em;" class="tabledataNew right tabledata" width="86px">30,49 EUR<br />16.05.<br />Ausserb&ouml;rslich<br /></td>
                   |
                   |
                   |	    <td style="padding-left:0.1em; padding-right:0.3em;border: 1px solid #E9E9E9;text-align: right;" class="tabledata" width="77px">5.183,30  </td>
                   |	    <td style="text-align: right; padding-right:0.4em;" class="tabledataNew right tabledata" width="75px"><span class="habentexttable">29,66</span><br /><span class="habentexttable">5.042,20 </span><br /><span class="habentexttable"><font color="green">141,10<font color="green"></span><br /><span class="positivtexttable"><font color="green">2,80</font></span><br /></td>
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"> <input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_pfeil_gestiegen.gif" value="Tendenz: gestiegen" disabled="disabled" style="cursor: default;height: 0.8333333330000001em;width: 0.9166666663em;" class="feldinfoTable" title="Tendenz: gestiegen" alt="Tendenz: gestiegen" /></td>
                   |	    <td style="padding-right: 0.5em;" class="tabledataNew right tabledata" width="20%"> <input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /> <a href="https://web.s-investor.de/app/index.jsp?INST_ID=0004093&ident=1&modul=detail/chart.jsp&modulparam=isin=DE000A1JSHG1" target="target"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_chart.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="B&ouml;rseninformationen" alt="B&ouml;rseninformationen" /></a> <input type="image" name="QKnqWJsetzLGUrRs" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_neartime.gif" value="Realtime-Kursabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Realtime-Kursabfrage" alt="Realtime-Kursabfrage" /> <input type="image" name="PYUlOtJiIEMeVsCF" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_minus_rot.gif" value="Wertpapier-Verkauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Verkauf" alt="Wertpapier-Verkauf" /> <input type="image" name="FIyxJCAEmFxlrHED" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_plus_rot.gif" value="Wertpapier-Kauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Kauf" alt="Wertpapier-Kauf" /> <input type="image" name="vBisRcokWeXijNtm" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Info" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Info" alt="Info" /></td>
                   |
                   |
                   |	</tr><tr><td colspan=8  style="font-weight: bold; color:#666666;">Sonstiges</td></tr><tr class="tableroweven">
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"><a name='toggleExpand1449521772' style='text-decoration:none;' /></td>
                   |	    <td style="padding-left:0.8em; padding-right:0.8em;border: 1px solid #E9E9E9;" class="tabledata" width="110px">E.ON SE TECHN.<br />WAHLDIV.
                   |<br />DE000ENAG1E0</td>
                   |	    <td style="text-align: right;" class="tabledataNew right tabledata" width="72px">210,00<br />&nbsp;St&uuml;ck<br /></td>
                   |
                   |	        <td style="text-align: left; padding-left:0.3em;" class="tabledataNew right tabledata" width="86px">nicht verf&uuml;gbar<br /></td>
                   |
                   |
                   |	    <td style="padding-left:0.1em; padding-right:0.3em;border: 1px solid #E9E9E9;text-align: right;" class="tabledata" width="77px"> </td>
                   |	    <td style="text-align: right; padding-right:0.4em;" class="tabledataNew right tabledata" width="75px"><br /></td>
                   |	    <td style="border: 1px solid #E9E9E9;" class="tabledata" width="1px"></td>
                   |	    <td style="padding-right: 0.5em;" class="tabledataNew right tabledata" width="20%"> <input type="image" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/basis/blank.gif" value="" disabled="disabled" style="cursor: default;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="" alt="" /> <a href="https://web.s-investor.de/app/index.jsp?INST_ID=0004093&ident=1&modul=detail/chart.jsp&modulparam=isin=DE000ENAG1E0" target="target"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_chart.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="B&ouml;rseninformationen" alt="B&ouml;rseninformationen" /></a> <input type="image" name="lXUmaRtMeOQuenBN" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_neartime.gif" value="Realtime-Kursabfrage" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Realtime-Kursabfrage" alt="Realtime-Kursabfrage" /> <input type="image" name="HGJebWLGZntfquAm" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_minus_rot.gif" value="Wertpapier-Verkauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Verkauf" alt="Wertpapier-Verkauf" /> <input type="image" name="uYrZhCFCjhJvwTYG" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_i_plus_rot.gif" value="Wertpapier-Kauf" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Wertpapier-Kauf" alt="Wertpapier-Kauf" /> <input type="image" name="ptFzzhIblRfrYHzv" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_banking_6.gif" value="Info" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="feldinfoTable" title="Info" alt="Info" /></td>
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
                   |            <td style="text-align: right; padding-right: 0.5em; padding-left: 0.3em;" class="tabledata tabledataNew left tabledata" width="72px">9.376,29</td>
                   |
                   |            <td style="text-align: right; padding-right: 0.3em; padding-left: 0.3em;" class="tabledata tabledataNew left tabledata" width="70px"><span class="habentexttable">13.766,85 </span><br /><span class="solltexttable"><font color="red">-4390,56<font color="red"></span><br /><span class="negativtexttable"><font color="red">-31,89</font></span><br /></td>
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
                   |	<div class="osppformgrund">
                   |	      <div class="osppinfoinhalt">
                   |	          <span> 1 von 4 Positionen wurden nicht bewertet.<br/></span>
                   |	        </div>
                   |	</div>
                   |
                   |<div class="osppformgrund">
                   |  <div class="osppinfoinhalt">* Alle Salden- und Kursangaben ohne Obligo.</div>
                   |</div>
                   |
                   |
                   |      <div class="osppformgrund">
                   |        <div class="osppinfoinhalt">
                   |          <span><em>**</em> Bitte beachten Sie unsere <a class="pfeilLink" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9zaWNoZXJoZWl0L3NpY2hlcmhlaXQtaW0taW50ZXJuZXQvYWt0dWVsbGUtbWVsZHVuZ2VuL2luZGV4LnBocA==" target="_blank" title="Neues Fenster: Sicherheitshinweise">Sicherheitshinweise</a> zum sicheren Umgang mit Download-Dateien.<br/></span>
                   |        </div>
                   |      </div>
                   |
                   |
                   |
                   |
                   |<!--****************** B U T T O N - B E R E I C H -->
                   |    <a name="pagebuttons" title="Buttonzeile" accesskey="5"></a> <div class="osppbuttonbereich"><h3 class="invisible">Buttonzeile</h3><a href="https://www.berliner-sparkasse.de/module/glossar/glossar_popup.php?words=Brokerage%20/%20Depotanzeige&complete=true" target="_blank"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_symbol_hilfe_rot.gif" style="cursor: pointer;height: 1.5833333327em;width: 2.1666666658000002em;" class="osppbuttonlink if5_hilfe if5_hilfe_finanz" title="Neues Fenster:Hilfe Depotanzeige" alt="Neues Fenster:Hilfe Depotanzeige" /></a><a href="https://banking.berliner-sparkasse.de/portal/print/?nc=1&pw=rpfErT61wITO7Ti&w=PORTFOLIO_SECURITIES_PRINT&r=c76324f8ab81b2c7" target="_blank"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_b_Druckan.png" style="cursor: pointer;height: 2.2499999991000004em;width: 10.499999995800001em;" class="osppbuttonlink" title="Neues Fenster:Druckansicht" alt="Neues Fenster:Druckansicht" /></a><input type="image" name="tChfFhWoPzmauLld" src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/ipo/if5_b_aktualisieren.png" value="Aktualisieren" accesskey="a" style="cursor: pointer;height: 2.2499999991000004em;width: 10.4166666625em;" class="osppbuttonlinklast" title="Aktualisieren" alt="Aktualisieren" /></div>
                   |
                   |      <body onload="jumpToAnchor('null')">
                   |
                   |     </body>
                   |
                   |    <input type="hidden" name="NtwjbygKtyARNEUL" value="c52f21eb025f6938" /><input type="hidden" name="rEoKOPDcwJYGKocr" value="85dcf09c296ef4d9" /></form>
                   |<script type="text/javascript">
                   |
                   |var focusControl = document.forms['a63b48c32d81dc67'].elements['QFXkksaljjtyDWQK'];
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
                   |<div class="if5_service c5_2 banking"><ul class="service_list"><li class="seite_senden" id="empfehlen"><a class="l3b c0" href="https://banking.berliner-sparkasse.de/portal/portal/_o:5ead6b7e018d10d9/?r=48b7291e0c3c0e45&amp;p=p.finanzstatus&amp;n=%2Fonlinebanking%2Ffinanzstatus%2F" id="empfehlen_link">Finanzstatus</a></li><li class="seite_drucken c0 druck_ohne_js" id="drucken"><script type="text/javascript">
                   |  <!--
                   |  document.write('<a class="l3b c0" href="javascript:window.print();">Seite drucken<span class="druck_ohne_js" id="druckwrap"><span class="if5_white_o">&nbsp;&nbsp;</span><span class="if5_rand"><span class="if5_verlauf_o">&nbsp;&nbsp;</span><span class="hw_druck_ohne_js">F&uuml;r diese Funktion muss JavaScript aktiviert sein.</span><span class="if5_verlauf_u">&nbsp;&nbsp;</span></span><span class="if5_white_u">&nbsp;&nbsp;</span></span></a>');
                   |  //-->
                   |  </script>
                   |<span style="display:none"><script type="text/javascript" language="JavaScript">/*<!--*/ document.getElementById('druckwrap').className = 'druck_mit_js';document.getElementById('drucken').className = 'seite_drucken c0'; //--></script></span></li><li class="seite_anfang c0"><a class="l3b c0" href="#">Seitenanfang</a></li></ul></div>
                   |
                   |
                   |		    			    			    	   	<div class="if5_footer"><ul class="footer_list l4"><li class="footer_listitem"><a title="Mobile Version" href="http://m.berliner-sparkasse.de"   target="_blank">Mobile Version</a></li><li class="footer_listitem"><a title="BIC: BELADEBEXXX" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvaW1wcmVzc3VtL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRmltcHJlc3N1bSUyRg=="   target="_top">BIC: BELADEBEXXX</a></li><li class="footer_listitem"><a title="Impressum" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvaW1wcmVzc3VtL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRmltcHJlc3N1bSUyRg=="   target="_top">Impressum</a></li><li class="footer_listitem"><a title="AGB" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvYWdiL2FsbGdlbWVpbmVfZ2VzY2hhZWZ0c2JlZGluZ3VuZ2VuL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRmFnYiUyRmFsbGdlbWVpbmVfZ2VzY2hhZWZ0c2JlZGluZ3VuZ2VuJTJG"   target="_top">AGB</a></li><li class="footer_listitem"><a title="Datenschutz" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvZGF0ZW5zY2h1dHovaW5kZXgucGhwP249JTJGbW9kdWxlJTJGc3RhdGljJTJGZGF0ZW5zY2h1dHolMkY="   target="_top">Datenschutz</a></li><li class="footer_listitem"><a title="Preise und Hinweise" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zdGF0aWMvcHJlaXNlX2hpbndlaXNlL3ZvcnRlaWxlL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnN0YXRpYyUyRnByZWlzZV9oaW53ZWlzZSUyRnZvcnRlaWxlJTJG"   target="_top">Preise und Hinweise</a></li><li class="footer_listitem"><a title="Sitemap" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL21vZHVsZS9zaXRlbWFwL2luZGV4LnBocD9uPSUyRm1vZHVsZSUyRnNpdGVtYXAlMkY="   target="_top">Sitemap</a></li></ul></div>
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
                   |                        <a href="/portal/portal/_ns:YlZlbG9jaXR5T25lQ29sdW1uX19ndi0xMzA1NTkwODg5RnJhZ21lbnQyN3xlQUNUSU9OPTE9VE9HR0xFX0RFVEFJTF9GQVY_/_o:5ead6b7e018d10d9/"> Favoriten</a>
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
                   |var ticktack=12.0*60*1000+new Date().getTime();tick();function tick() {var timeoutID;var s;var t=ticktack-new Date().getTime();t=(t<0)?0:Math.round(t/1000);s=""+ ((t>60)?Math.ceil(t/60).toFixed(0)+" Min.":t.toFixed(0)+" Sek.");document.getElementById("sdauer").innerHTML=" Zu Ihrer Sicherheit erfolgt die automatische Abmeldung in "+s;if (t!=0) timeoutID = window.setTimeout("tick()",(t>60) ? 6000 : 1000);else {window.clearTimeout(timeoutID);location.replace("/portal/portal/_o:5ead6b7e018d10d9/LogoutTimeout");}}
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
                   |		    			    			    	    <div class="if5_container if5_werbung" style=""><div class="rounded"><div class="if5_white_o_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --><div class="if5_rand"><div class="if5_verlauf_o">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --><!--  Backgroundimage wird per Inline-Style gesetzt, da Aktionsfl&auml;che   --><div class="rounded_content"><h4><span>Tagesgeldkonto Direkt</span></h4><div class="clip"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pool/contentelements/container/tagesgeldkonto_direkt_x80N1o/bg_gen.jpg" /></div><div class="textsection" style=""><h3><span></span></h3><p>Die ideale Erg&auml;nzung zum Girokonto - einfach &amp; bequem Geld anlegen oder monatlich sparen.</p><div class="if5_container_link"><div><a title="Jetzt eröffnen" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9zcGFyZW5fYW5sZWdlbi90YWdlc2dlbGQvdm9ydGVpbGUvaW5kZXgucGhwP249JTJGcHJpdmF0a3VuZGVuJTJGc3BhcmVuX2FubGVnZW4lMkZ0YWdlc2dlbGQlMkZ2b3J0ZWlsZSUyRg=="   target="_top">Jetzt er&ouml;ffnen</a></div></div></div></div><div class="if5_verlauf_u">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --></div><!--  NI: Rand  --><div class="if5_white_u_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --></div><!--  NI: Ende umschliessendes DIV  --></div>
                   |
                   |
                   |
                   |
                   |
                   |
                   |		    			    			    	    <div class="if5_container if5_werbung" style=""><div class="rounded"><div class="if5_white_o_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --><div class="if5_rand"><div class="if5_verlauf_o">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --><!--  Backgroundimage wird per Inline-Style gesetzt, da Aktionsfl&auml;che   --><div class="rounded_content"><h4><span>Bitte beachten!</span></h4><div class="clip"><img src="/ifdata/10050000/IPSTANDARD/4/content/www/pixel/container/bsk_co153_wartungsarbeiten.jpg" /></div><div class="textsection" style="top:0.2em;"><h3><span>Wegen Wartungsarbeiten</span></h3><p>steht Ihnen das Online-Brokerage am <strong> Samstag, den 31. Mai 2014 zwischen 7 und 11 Uhr </strong> zeitweise leider nicht zur Verf&uuml;gung. Vielen Dank f&uuml;r Ihr Verst&auml;ndnis.</p><div class="if5_container_link"><div><a title="Aktuelle Sicherheitsmeldungen" href="https://www.berliner-sparkasse.de/module/tx_entry/anmelden.php?_tourl=aHR0cHM6Ly93d3cuYmVybGluZXItc3Bhcmthc3NlLmRlL3ByaXZhdGt1bmRlbi9iYW5raW5nL2FrdHVlbGxlLWhpbndlaXNlL2FyY2hpdl8yMDE0L3VlYmVyc2ljaHQvaW5kZXgucGhwP249JTJGb25saW5lYmFua2luZyUyRnNlcnZpY2UlMkZha3R1ZWxsZXMlMkYyMDE0JTJG"   target="_top">Aktuelles</a></div></div></div></div><div class="if5_verlauf_u">&nbsp;</div><!--  NI: Verlauf hinzugef&uuml;gt  --></div><!--  NI: Rand  --><div class="if5_white_u_rounded">&nbsp;</div><!--  NI: runde Ecke im Verlauf  --></div><!--  NI: Ende umschliessendes DIV  --></div>
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
                   |		    			    		    			    		  		  			<br />
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
                 """.stripMargin
      val data = new SparKassenClient().parseStockOverview(html)
      println(data)
      //in test the last character of stock name is removed put when main is running than althing is okay there is a special character in the html
      data.accounts.get("COMMERZBANK A").get.value must beEqualTo(1417.00)
    }
  }
}
