/**
 * Created by faiter on 7/10/17.
 */

String.prototype.hashCode = function() {
    var hash = 0, i, chr;
    if (this.length === 0) return hash;
    for (i = 0; i < this.length; i++) {
        chr   = this.charCodeAt(i);
        hash  = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
};

function setCookieHashed(toHash){

    $.cookie(toHash.hashCode().toString(), 'true')
    console.log("hashed cookie: "+toHash.hashCode())

}

function hasCookieHashed(toHash) {

    var cookie = $.cookie(toHash.hashCode().toString())

    console.log("hash: "+toHash.hashCode()+" - cookie: "+cookie)

    return cookie

}