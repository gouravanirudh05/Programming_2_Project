(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[928],{6658:function(e,t,n){Promise.resolve().then(n.bind(n,3262))},3262:function(e,t,n){"use strict";n.r(t);var a=n(7437),r=n(3464),c=n(2265);t.default=function(){let[e,t]=(0,c.useState)(null),[n,s]=(0,c.useState)(null);return((0,c.useEffect)(()=>{{let e=localStorage.getItem("token");r.Z.defaults.headers.common.Authorization=e?"Bearer ".concat(e):"",r.Z.get("/admin/protected-data").then(e=>{t(e.data)}).catch(e=>{s(e.message)})}},[]),n)?(0,a.jsxs)(a.Fragment,{children:["Error: ",n]}):e?(0,a.jsx)(a.Fragment,{children:e}):(0,a.jsx)(a.Fragment,{children:"Loading..."})}}},function(e){e.O(0,[464,971,117,744],function(){return e(e.s=6658)}),_N_E=e.O()}]);