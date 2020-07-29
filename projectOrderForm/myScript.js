var totalPrice = 0;
function changeContent(boxElement,divToChange,innerDiv,priceDiv)
{
	var i = boxElement.value.indexOf("|");
	var cleaned = boxElement.value.substring(0,i);
	var price = parseInt(boxElement.value.substring(i+1,boxElement.value.length));

		if(boxElement.checked)
		{
		s = document.getElementById(divToChange).innerHTML;
		s += "<div class="+innerDiv+">" + cleaned + "</div>";
		document.getElementById(divToChange).innerHTML = s;
		document.getElementById("price").innerHTML = "Pris:" + updatePrice(price,totalPrice) + "SEK";
		totalPrice += price;
		}
		
		else
		{
		s = document.getElementById(divToChange).innerHTML;
		toReplace = "<div class=\""+innerDiv+"\">" + cleaned + "</div>";
		s = s.replace(toReplace, "");
		document.getElementById(divToChange).innerHTML = s;
		totalPrice -= price;
		document.getElementById("price").innerHTML = "Pris:" + totalPrice + "SEK";
		}

}


function updatePrice(price,totalPrice)
{
	return totalPrice = price + totalPrice;
}

function showAlert()
{
	alert("Kan ej beställas");
	var checkbox = document.getElementById("mycheck").checked;
	var checkboxtwo = document.getElementById("mychecktwo").checked;
	if(checkbox==true||checkboxtwo==true)
	{
	document.getElementById("mycheck").checked = false;
	document.getElementById("mychecktwo").checked = false;
	}
	return false;
}

function kollaFalt()
{
	
namnmonster=/^[A-ZÅÄÖ][a-zåäö]+$/;
namninput=document.formular.namn.value;
	if((namnmonster.test(namninput))!=1)
	alert("Fel inmatning på namn!");
efternamninput=document.formular.efternamn.value;
	if((namnmonster.test(efternamninput))!=1)
	alert("Fel inmatning på efternamn!");
epostmonster=/^\w+@\w+\.+\w+$/;
epostinput=document.formular.epost.value;
	if((epostmonster.test(epostinput))!=1)
	alert("Fel inmatning på epost!");
adressmonster=/^[A-ZÅÄÖ][a-zåäö]+[a-zåäö\s]+[0-9]{2.4}$/;
adressinput=document.formular.adress.value;
	if((adressmonster.test(adressinput))!=1)
	alert("Fel inmatning på adress!");
	if(totalPrice==0)
	alert("Välj skivor!");
	if (document.formular.mediatyp.value != "on")
		alert("välj mediatyp");
	return false;

}

