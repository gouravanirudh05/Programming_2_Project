(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[199],{2006:function(e,s,t){Promise.resolve().then(t.bind(t,5742))},5742:function(e,s,t){"use strict";t.r(s),t.d(s,{default:function(){return x}});var a=t(7437),r=t(2265),n=t(2381),i=t(279),l=t(5060),o=t(5291),d=t(9820),c=t(3804),m=t(4291),p=t(5301);let u=[{roomID:"R101",roomType:"DELUXE",capacity:2,housekeepingLast:"2024-01-17T08:00:00"},{roomID:"R102",roomType:"SUITE",capacity:4,housekeepingLast:"2024-01-17T09:00:00"},{roomID:"R103",roomType:"STANDARD",capacity:2,housekeepingLast:"2024-01-17T10:00:00"}],f=[{roomTypeID:"RT1",name:"STANDARD",tariff:100,amenities:["TV","Wi-Fi"]},{roomTypeID:"RT2",name:"DELUXE",tariff:150,amenities:["TV","Wi-Fi","Mini Bar"]},{roomTypeID:"RT3",name:"SUITE",tariff:250,amenities:["TV","Wi-Fi","Mini Bar","Jacuzzi"]}];function x(){let[e,s]=(0,r.useState)(u),[t,x]=(0,r.useState)(f),[h,j]=(0,r.useState)({roomID:"",roomType:"",capacity:0,housekeepingLast:""}),[g,y]=(0,r.useState)({roomTypeID:"",name:"",tariff:0,amenities:[]}),[N,v]=(0,r.useState)(!1),[b,w]=(0,r.useState)(!1),[T,I]=(0,r.useState)(null),[D,R]=(0,r.useState)(null),C=t=>{s(e.filter(e=>e.roomID!==t))},k=e=>{x(t.filter(s=>s.roomTypeID!==e))};return(0,a.jsxs)("div",{className:"space-y-6",children:[(0,a.jsx)("h1",{className:"text-3xl font-bold tracking-tight",children:"Room Management"}),(0,a.jsxs)(p.mQ,{defaultValue:"rooms",children:[(0,a.jsxs)(p.dr,{children:[(0,a.jsx)(p.SP,{value:"rooms",children:"Rooms"}),(0,a.jsx)(p.SP,{value:"roomTypes",children:"Room Types"})]}),(0,a.jsx)(p.nU,{value:"rooms",children:(0,a.jsxs)(d.Zb,{children:[(0,a.jsxs)(d.Ol,{className:"flex flex-row items-center justify-between",children:[(0,a.jsx)(d.ll,{children:"Rooms"}),(0,a.jsxs)(m.Vq,{open:N,onOpenChange:v,children:[(0,a.jsx)(m.hg,{asChild:!0,children:(0,a.jsx)(n.z,{children:"Add Room"})}),(0,a.jsxs)(m.cZ,{children:[(0,a.jsxs)(m.fK,{children:[(0,a.jsx)(m.$N,{children:"Add New Room"}),(0,a.jsx)(m.Be,{children:"Enter the details for the new room."})]}),(0,a.jsxs)("div",{className:"grid gap-4 py-4",children:[(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"roomID",className:"text-right",children:"Room ID"}),(0,a.jsx)(i.I,{id:"roomID",value:h.roomID,onChange:e=>j({...h,roomID:e.target.value}),className:"col-span-3"})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"roomType",className:"text-right",children:"Room Type"}),(0,a.jsxs)(o.Ph,{onValueChange:e=>j({...h,roomType:e}),children:[(0,a.jsx)(o.i4,{className:"col-span-3",children:(0,a.jsx)(o.ki,{placeholder:"Select room type"})}),(0,a.jsx)(o.Bw,{children:t.map(e=>(0,a.jsx)(o.Ql,{value:e.name,children:e.name},e.roomTypeID))})]})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"capacity",className:"text-right",children:"Capacity"}),(0,a.jsx)(i.I,{id:"capacity",type:"number",value:h.capacity,onChange:e=>j({...h,capacity:parseInt(e.target.value)}),className:"col-span-3"})]})]}),(0,a.jsx)(m.cN,{children:(0,a.jsx)(n.z,{onClick:()=>{s([...e,h]),j({roomID:"",roomType:"",capacity:0,housekeepingLast:""}),v(!1)},children:"Add Room"})})]})]})]}),(0,a.jsx)(d.aY,{children:(0,a.jsxs)(c.iA,{children:[(0,a.jsx)(c.xD,{children:(0,a.jsxs)(c.SC,{children:[(0,a.jsx)(c.ss,{children:"Room ID"}),(0,a.jsx)(c.ss,{children:"Type"}),(0,a.jsx)(c.ss,{children:"Capacity"}),(0,a.jsx)(c.ss,{children:"Last Housekeeping"}),(0,a.jsx)(c.ss,{children:"Actions"})]})}),(0,a.jsx)(c.RM,{children:e.map(e=>(0,a.jsxs)(c.SC,{children:[(0,a.jsx)(c.pj,{children:e.roomID}),(0,a.jsx)(c.pj,{children:e.roomType}),(0,a.jsx)(c.pj,{children:e.capacity}),(0,a.jsx)(c.pj,{children:new Date(e.housekeepingLast).toLocaleString()}),(0,a.jsxs)(c.pj,{children:[(0,a.jsx)(n.z,{variant:"outline",size:"sm",className:"mr-2",onClick:()=>I(e),children:"Edit"}),(0,a.jsx)(n.z,{variant:"destructive",size:"sm",onClick:()=>C(e.roomID),children:"Delete"})]})]},e.roomID))})]})})]})}),(0,a.jsx)(p.nU,{value:"roomTypes",children:(0,a.jsxs)(d.Zb,{children:[(0,a.jsxs)(d.Ol,{className:"flex flex-row items-center justify-between",children:[(0,a.jsx)(d.ll,{children:"Room Types"}),(0,a.jsxs)(m.Vq,{open:b,onOpenChange:w,children:[(0,a.jsx)(m.hg,{asChild:!0,children:(0,a.jsx)(n.z,{children:"Add Room Type"})}),(0,a.jsxs)(m.cZ,{children:[(0,a.jsxs)(m.fK,{children:[(0,a.jsx)(m.$N,{children:"Add New Room Type"}),(0,a.jsx)(m.Be,{children:"Enter the details for the new room type."})]}),(0,a.jsxs)("div",{className:"grid gap-4 py-4",children:[(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"roomTypeID",className:"text-right",children:"Room Type ID"}),(0,a.jsx)(i.I,{id:"roomTypeID",value:g.roomTypeID,onChange:e=>y({...g,roomTypeID:e.target.value}),className:"col-span-3"})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"name",className:"text-right",children:"Name"}),(0,a.jsx)(i.I,{id:"name",value:g.name,onChange:e=>y({...g,name:e.target.value}),className:"col-span-3"})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"tariff",className:"text-right",children:"Tariff"}),(0,a.jsx)(i.I,{id:"tariff",type:"number",value:g.tariff,onChange:e=>y({...g,tariff:parseFloat(e.target.value)}),className:"col-span-3"})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"amenities",className:"text-right",children:"Amenities"}),(0,a.jsx)(i.I,{id:"amenities",value:g.amenities.join(", "),onChange:e=>y({...g,amenities:e.target.value.split(", ")}),className:"col-span-3"})]})]}),(0,a.jsx)(m.cN,{children:(0,a.jsx)(n.z,{onClick:()=>{x([...t,g]),y({roomTypeID:"",name:"",tariff:0,amenities:[]}),w(!1)},children:"Add Room Type"})})]})]})]}),(0,a.jsx)(d.aY,{children:(0,a.jsxs)(c.iA,{children:[(0,a.jsx)(c.xD,{children:(0,a.jsxs)(c.SC,{children:[(0,a.jsx)(c.ss,{children:"Room Type ID"}),(0,a.jsx)(c.ss,{children:"Name"}),(0,a.jsx)(c.ss,{children:"Tariff"}),(0,a.jsx)(c.ss,{children:"Amenities"}),(0,a.jsx)(c.ss,{children:"Actions"})]})}),(0,a.jsx)(c.RM,{children:t.map(e=>(0,a.jsxs)(c.SC,{children:[(0,a.jsx)(c.pj,{children:e.roomTypeID}),(0,a.jsx)(c.pj,{children:e.name}),(0,a.jsxs)(c.pj,{children:["$",e.tariff]}),(0,a.jsx)(c.pj,{children:e.amenities.join(", ")}),(0,a.jsxs)(c.pj,{children:[(0,a.jsx)(n.z,{variant:"outline",size:"sm",className:"mr-2",onClick:()=>R(e),children:"Edit"}),(0,a.jsx)(n.z,{variant:"destructive",size:"sm",onClick:()=>k(e.roomTypeID),children:"Delete"})]})]},e.roomTypeID))})]})})]})})]}),T&&(0,a.jsx)(m.Vq,{open:!!T,onOpenChange:()=>I(null),children:(0,a.jsxs)(m.cZ,{children:[(0,a.jsx)(m.fK,{children:(0,a.jsx)(m.$N,{children:"Edit Room"})}),(0,a.jsxs)("div",{className:"grid gap-4 py-4",children:[(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"edit-roomID",className:"text-right",children:"Room ID"}),(0,a.jsx)(i.I,{id:"edit-roomID",value:T.roomID,onChange:e=>I({...T,roomID:e.target.value}),className:"col-span-3"})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"edit-roomType",className:"text-right",children:"Room Type"}),(0,a.jsxs)(o.Ph,{value:T.roomType,onValueChange:e=>I({...T,roomType:e}),children:[(0,a.jsx)(o.i4,{className:"col-span-3",children:(0,a.jsx)(o.ki,{placeholder:"Select room type"})}),(0,a.jsx)(o.Bw,{children:t.map(e=>(0,a.jsx)(o.Ql,{value:e.name,children:e.name},e.roomTypeID))})]})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"edit-capacity",className:"text-right",children:"Capacity"}),(0,a.jsx)(i.I,{id:"edit-capacity",type:"number",value:T.capacity,onChange:e=>I({...T,capacity:parseInt(e.target.value)}),className:"col-span-3"})]})]}),(0,a.jsx)(m.cN,{children:(0,a.jsx)(n.z,{onClick:()=>{T&&(s(e.map(e=>e.roomID===T.roomID?T:e)),I(null))},children:"Update Room"})})]})}),D&&(0,a.jsx)(m.Vq,{open:!!D,onOpenChange:()=>R(null),children:(0,a.jsxs)(m.cZ,{children:[(0,a.jsx)(m.fK,{children:(0,a.jsx)(m.$N,{children:"Edit Room Type"})}),(0,a.jsxs)("div",{className:"grid gap-4 py-4",children:[(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"edit-roomTypeID",className:"text-right",children:"Room Type ID"}),(0,a.jsx)(i.I,{id:"edit-roomTypeID",value:D.roomTypeID,onChange:e=>R({...D,roomTypeID:e.target.value}),className:"col-span-3"})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"edit-name",className:"text-right",children:"Name"}),(0,a.jsx)(i.I,{id:"edit-name",value:D.name,onChange:e=>R({...D,name:e.target.value}),className:"col-span-3"})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"edit-tariff",className:"text-right",children:"Tariff"}),(0,a.jsx)(i.I,{id:"edit-tariff",type:"number",value:D.tariff,onChange:e=>R({...D,tariff:parseFloat(e.target.value)}),className:"col-span-3"})]}),(0,a.jsxs)("div",{className:"grid grid-cols-4 items-center gap-4",children:[(0,a.jsx)(l._,{htmlFor:"edit-amenities",className:"text-right",children:"Amenities"}),(0,a.jsx)(i.I,{id:"edit-amenities",value:D.amenities.join(", "),onChange:e=>R({...D,amenities:e.target.value.split(", ")}),className:"col-span-3"})]})]}),(0,a.jsx)(m.cN,{children:(0,a.jsx)(n.z,{onClick:()=>{D&&(x(t.map(e=>e.roomTypeID===D.roomTypeID?D:e)),R(null))},children:"Update Room Type"})})]})})]})}},2381:function(e,s,t){"use strict";t.d(s,{z:function(){return d}});var a=t(7437),r=t(2265),n=t(7053),i=t(7712),l=t(3448);let o=(0,i.j)("inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-medium transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:pointer-events-none disabled:opacity-50 [&_svg]:pointer-events-none [&_svg]:size-4 [&_svg]:shrink-0",{variants:{variant:{default:"bg-primary text-primary-foreground shadow hover:bg-primary/90",destructive:"bg-destructive text-destructive-foreground shadow-sm hover:bg-destructive/90",outline:"border border-input bg-background shadow-sm hover:bg-accent hover:text-accent-foreground",secondary:"bg-secondary text-secondary-foreground shadow-sm hover:bg-secondary/80",ghost:"hover:bg-accent hover:text-accent-foreground",link:"text-primary underline-offset-4 hover:underline"},size:{default:"h-9 px-4 py-2",sm:"h-8 rounded-md px-3 text-xs",lg:"h-10 rounded-md px-8",icon:"h-9 w-9"}},defaultVariants:{variant:"default",size:"default"}}),d=r.forwardRef((e,s)=>{let{className:t,variant:r,size:i,asChild:d=!1,...c}=e,m=d?n.g7:"button";return(0,a.jsx)(m,{className:(0,l.cn)(o({variant:r,size:i,className:t})),ref:s,...c})});d.displayName="Button"},9820:function(e,s,t){"use strict";t.d(s,{Ol:function(){return l},Zb:function(){return i},aY:function(){return d},eW:function(){return c},ll:function(){return o}});var a=t(7437),r=t(2265),n=t(3448);let i=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("div",{ref:s,className:(0,n.cn)("rounded-xl border bg-card text-card-foreground shadow",t),...r})});i.displayName="Card";let l=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("div",{ref:s,className:(0,n.cn)("flex flex-col space-y-1.5 p-6",t),...r})});l.displayName="CardHeader";let o=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("div",{ref:s,className:(0,n.cn)("font-semibold leading-none tracking-tight",t),...r})});o.displayName="CardTitle",r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("div",{ref:s,className:(0,n.cn)("text-sm text-muted-foreground",t),...r})}).displayName="CardDescription";let d=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("div",{ref:s,className:(0,n.cn)("p-6 pt-0",t),...r})});d.displayName="CardContent";let c=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("div",{ref:s,className:(0,n.cn)("flex items-center p-6 pt-0",t),...r})});c.displayName="CardFooter"},4291:function(e,s,t){"use strict";t.d(s,{$N:function(){return x},Be:function(){return h},Vq:function(){return o},cN:function(){return f},cZ:function(){return p},fK:function(){return u},hg:function(){return d}});var a=t(7437),r=t(2265),n=t(9027),i=t(2489),l=t(3448);let o=n.fC,d=n.xz,c=n.h_;n.x8;let m=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)(n.aV,{ref:s,className:(0,l.cn)("fixed inset-0 z-50 bg-black/80  data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0",t),...r})});m.displayName=n.aV.displayName;let p=r.forwardRef((e,s)=>{let{className:t,children:r,...o}=e;return(0,a.jsxs)(c,{children:[(0,a.jsx)(m,{}),(0,a.jsxs)(n.VY,{ref:s,className:(0,l.cn)("fixed left-[50%] top-[50%] z-50 grid w-full max-w-lg translate-x-[-50%] translate-y-[-50%] gap-4 border bg-background p-6 shadow-lg duration-200 data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[state=closed]:slide-out-to-left-1/2 data-[state=closed]:slide-out-to-top-[48%] data-[state=open]:slide-in-from-left-1/2 data-[state=open]:slide-in-from-top-[48%] sm:rounded-lg",t),...o,children:[r,(0,a.jsxs)(n.x8,{className:"absolute right-4 top-4 rounded-sm opacity-70 ring-offset-background transition-opacity hover:opacity-100 focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 disabled:pointer-events-none data-[state=open]:bg-accent data-[state=open]:text-muted-foreground",children:[(0,a.jsx)(i.Z,{className:"h-4 w-4"}),(0,a.jsx)("span",{className:"sr-only",children:"Close"})]})]})]})});p.displayName=n.VY.displayName;let u=e=>{let{className:s,...t}=e;return(0,a.jsx)("div",{className:(0,l.cn)("flex flex-col space-y-1.5 text-center sm:text-left",s),...t})};u.displayName="DialogHeader";let f=e=>{let{className:s,...t}=e;return(0,a.jsx)("div",{className:(0,l.cn)("flex flex-col-reverse sm:flex-row sm:justify-end sm:space-x-2",s),...t})};f.displayName="DialogFooter";let x=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)(n.Dx,{ref:s,className:(0,l.cn)("text-lg font-semibold leading-none tracking-tight",t),...r})});x.displayName=n.Dx.displayName;let h=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)(n.dk,{ref:s,className:(0,l.cn)("text-sm text-muted-foreground",t),...r})});h.displayName=n.dk.displayName},279:function(e,s,t){"use strict";t.d(s,{I:function(){return i}});var a=t(7437),r=t(2265),n=t(3448);let i=r.forwardRef((e,s)=>{let{className:t,type:r,...i}=e;return(0,a.jsx)("input",{type:r,className:(0,n.cn)("flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-base shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium file:text-foreground placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50 md:text-sm",t),ref:s,...i})});i.displayName="Input"},5060:function(e,s,t){"use strict";t.d(s,{_:function(){return d}});var a=t(7437),r=t(2265),n=t(6394),i=t(7712),l=t(3448);let o=(0,i.j)("text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"),d=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)(n.f,{ref:s,className:(0,l.cn)(o(),t),...r})});d.displayName=n.f.displayName},5291:function(e,s,t){"use strict";t.d(s,{Bw:function(){return x},Ph:function(){return c},Ql:function(){return h},i4:function(){return p},ki:function(){return m}});var a=t(7437),r=t(2265),n=t(561),i=t(875),l=t(2135),o=t(401),d=t(3448);let c=n.fC;n.ZA;let m=n.B4,p=r.forwardRef((e,s)=>{let{className:t,children:r,...l}=e;return(0,a.jsxs)(n.xz,{ref:s,className:(0,d.cn)("flex h-9 w-full items-center justify-between whitespace-nowrap rounded-md border border-input bg-transparent px-3 py-2 text-sm shadow-sm ring-offset-background placeholder:text-muted-foreground focus:outline-none focus:ring-1 focus:ring-ring disabled:cursor-not-allowed disabled:opacity-50 [&>span]:line-clamp-1",t),...l,children:[r,(0,a.jsx)(n.JO,{asChild:!0,children:(0,a.jsx)(i.Z,{className:"h-4 w-4 opacity-50"})})]})});p.displayName=n.xz.displayName;let u=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)(n.u_,{ref:s,className:(0,d.cn)("flex cursor-default items-center justify-center py-1",t),...r,children:(0,a.jsx)(l.Z,{className:"h-4 w-4"})})});u.displayName=n.u_.displayName;let f=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)(n.$G,{ref:s,className:(0,d.cn)("flex cursor-default items-center justify-center py-1",t),...r,children:(0,a.jsx)(i.Z,{className:"h-4 w-4"})})});f.displayName=n.$G.displayName;let x=r.forwardRef((e,s)=>{let{className:t,children:r,position:i="popper",...l}=e;return(0,a.jsx)(n.h_,{children:(0,a.jsxs)(n.VY,{ref:s,className:(0,d.cn)("relative z-50 max-h-96 min-w-[8rem] overflow-hidden rounded-md border bg-popover text-popover-foreground shadow-md data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2","popper"===i&&"data-[side=bottom]:translate-y-1 data-[side=left]:-translate-x-1 data-[side=right]:translate-x-1 data-[side=top]:-translate-y-1",t),position:i,...l,children:[(0,a.jsx)(u,{}),(0,a.jsx)(n.l_,{className:(0,d.cn)("p-1","popper"===i&&"h-[var(--radix-select-trigger-height)] w-full min-w-[var(--radix-select-trigger-width)]"),children:r}),(0,a.jsx)(f,{})]})})});x.displayName=n.VY.displayName,r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)(n.__,{ref:s,className:(0,d.cn)("px-2 py-1.5 text-sm font-semibold",t),...r})}).displayName=n.__.displayName;let h=r.forwardRef((e,s)=>{let{className:t,children:r,...i}=e;return(0,a.jsxs)(n.ck,{ref:s,className:(0,d.cn)("relative flex w-full cursor-default select-none items-center rounded-sm py-1.5 pl-2 pr-8 text-sm outline-none focus:bg-accent focus:text-accent-foreground data-[disabled]:pointer-events-none data-[disabled]:opacity-50",t),...i,children:[(0,a.jsx)("span",{className:"absolute right-2 flex h-3.5 w-3.5 items-center justify-center",children:(0,a.jsx)(n.wU,{children:(0,a.jsx)(o.Z,{className:"h-4 w-4"})})}),(0,a.jsx)(n.eT,{children:r})]})});h.displayName=n.ck.displayName,r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)(n.Z0,{ref:s,className:(0,d.cn)("-mx-1 my-1 h-px bg-muted",t),...r})}).displayName=n.Z0.displayName},3804:function(e,s,t){"use strict";t.d(s,{RM:function(){return o},SC:function(){return d},iA:function(){return i},pj:function(){return m},ss:function(){return c},xD:function(){return l}});var a=t(7437),r=t(2265),n=t(3448);let i=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("div",{className:"relative w-full overflow-auto",children:(0,a.jsx)("table",{ref:s,className:(0,n.cn)("w-full caption-bottom text-sm",t),...r})})});i.displayName="Table";let l=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("thead",{ref:s,className:(0,n.cn)("[&_tr]:border-b",t),...r})});l.displayName="TableHeader";let o=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("tbody",{ref:s,className:(0,n.cn)("[&_tr:last-child]:border-0",t),...r})});o.displayName="TableBody",r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("tfoot",{ref:s,className:(0,n.cn)("border-t bg-muted/50 font-medium [&>tr]:last:border-b-0",t),...r})}).displayName="TableFooter";let d=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("tr",{ref:s,className:(0,n.cn)("border-b transition-colors hover:bg-muted/50 data-[state=selected]:bg-muted",t),...r})});d.displayName="TableRow";let c=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("th",{ref:s,className:(0,n.cn)("h-10 px-2 text-left align-middle font-medium text-muted-foreground [&:has([role=checkbox])]:pr-0 [&>[role=checkbox]]:translate-y-[2px]",t),...r})});c.displayName="TableHead";let m=r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("td",{ref:s,className:(0,n.cn)("p-2 align-middle [&:has([role=checkbox])]:pr-0 [&>[role=checkbox]]:translate-y-[2px]",t),...r})});m.displayName="TableCell",r.forwardRef((e,s)=>{let{className:t,...r}=e;return(0,a.jsx)("caption",{ref:s,className:(0,n.cn)("mt-4 text-sm text-muted-foreground",t),...r})}).displayName="TableCaption"}},function(e){e.O(0,[317,604,159,301,971,117,744],function(){return e(e.s=2006)}),_N_E=e.O()}]);