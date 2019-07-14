var vTest = new Vue({
	el:'#app',
	data:function(){
		return{
			book:'',
		}
	},
	mounted(){
		console.log('12312312312213')
	},
	methods:{
		test2: function(){
			console.log(this.book)
		}
	}
	
});
