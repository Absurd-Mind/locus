(function(k){var h={undHash:/_|-/,colons:/::/,words:/([A-Z]+)([A-Z][a-z])/g,lowerUpper:/([a-z\d])([A-Z])/g,dash:/([a-z\d])([A-Z])/g,replacer:/\{([^\}]+)\}/g},q=function(a,d,m){var l=d||window;a=a?a.split(/\./):[];for(var f=0;f<a.length-1&&l;f++)l=l[a[f]]||m&&(l[a[f]]={});if(a.length==0)return d;d=l[a[f]]||m&&(l[a[f]]={});m===false&&delete l[a[f]];return d},p=k.String={getObject:q,strip:function(a){return a.replace(/^\s+/,"").replace(/\s+$/,"")},capitalize:function(a){return a.charAt(0).toUpperCase()+
a.substr(1)},endsWith:function(a,d){var m=a.length-d.length;return m>=0&&a.lastIndexOf(d)===m},camelize:function(a){a=a.split(h.undHash);var d=1;for(a[0]=a[0].charAt(0).toLowerCase()+a[0].substr(1);d<a.length;d++)a[d]=p.capitalize(a[d]);return a.join("")},classize:function(a){a=a.split(h.undHash);for(var d=0;d<a.length;d++)a[d]=p.capitalize(a[d]);return a.join("")},niceName:function(a){a=a.split(h.undHash);for(var d=0;d<a.length;d++)a[d]=p.capitalize(a[d]);return a.join(" ")},underscore:function(a){return a.replace(h.colons,
"/").replace(h.words,"$1_$2").replace(h.lowerUpper,"$1_$2").replace(h.dash,"_").toLowerCase()},sub:function(a,d,m){return a.replace(h.replacer,function(l,f){return q(f,d,!m).toString()})}}})(jQuery);
(function(k){var h=false,q=k.makeArray,p=k.isFunction,a=k.isArray,d=function(b,e){return b.concat(q(e))},m=/xyz/.test(function(){})?/\b_super\b/:/.*/,l=function(b,e,g){g=g||b;for(var c in b)g[c]=p(b[c])&&p(e[c])&&m.test(b[c])?function(n,o){return function(){var i=this._super,j;this._super=e[n];j=o.apply(this,arguments);this._super=i;return j}}(c,b[c]):b[c]},f=k.Class=function(){arguments.length&&f.extend.apply(f,arguments)};k.extend(f,{callback:function(b){var e=q(arguments),g;b=e.shift();a(b)||(b=
[b]);g=this;return function(){for(var c=d(e,arguments),n,o=b.length,i=0,j;i<o;i++)if(j=b[i]){if((n=typeof j=="string")&&g._set_called)g.called=j;c=(n?g[j]:j).apply(g,c||[]);if(i<o-1)c=!a(c)||c._use_call?[c]:c}return c}},getObject:k.String.getObject,newInstance:function(){var b=this.rawInstance(),e;if(b.setup)e=b.setup.apply(b,arguments);if(b.init)b.init.apply(b,a(e)?e:arguments);return b},setup:function(b){this.defaults=k.extend(true,{},b.defaults,this.defaults);return arguments},rawInstance:function(){h=
true;var b=new this;h=false;return b},extend:function(b,e,g){function c(){if(!h)return this.constructor!==c&&arguments.length?arguments.callee.extend.apply(arguments.callee,arguments):this.Class.newInstance.apply(this.Class,arguments)}if(typeof b!="string"){g=e;e=b;b=null}if(!g){g=e;e=null}g=g||{};var n=this,o=this.prototype,i,j,r,s;h=true;s=new this;h=false;l(g,o,s);for(i in this)if(this.hasOwnProperty(i)&&k.inArray(i,["prototype","defaults","getObject"])==-1)c[i]=this[i];l(e,this,c);if(b){r=b.split(/\./);
j=r.pop();r=o=f.getObject(r.join("."),window,true);o[j]=c}k.extend(c,{prototype:s,namespace:r,shortName:j,constructor:c,fullName:b});c.prototype.Class=c.prototype.constructor=c;n=c.setup.apply(c,d([n],arguments));if(c.init)c.init.apply(c,n||[]);return c}});f.prototype.callback=f.callback})(jQuery);
