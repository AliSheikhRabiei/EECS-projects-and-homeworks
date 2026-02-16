package model;

public class Monitor extends Follower {
	
	private int[] max;
	private int[] view;
	private int[] sum;
	
	public Monitor(String name, int maxChannel) {
		this.name = name;
		this.maxChannel = maxChannel;
		this.numChannel=0;
		this.Channels=new Channel[maxChannel];
		this.max=new int[maxChannel];
		this.sum=new int[maxChannel];
		this.view=new int[maxChannel];
	}
	
	@Override
	public String getTitle() {
		return "Monitor";
	}
	
	@Override
	public String toString() {
		String s="";
		String f="";
		
		if(this.numChannel==0) f="no channels";
		else {
			f+="[";
			for(int j=0; j<this.numChannel; j++) {
				if(this.view[j]==0) {
				f+=this.Channels[j].getName();
				}
				else {
					double avg= (double) this.sum[j]/this.view[j];
					f+=String.format("%s {#views: %d, max watch time: %d, avg watch time: %.2f}"
							,this.Channels[j].getName(), this.view[j], this.max[j], avg);
				}
				if(j<this.numChannel-1) {
					f+=", ";
				}
			}
			f+="]";
		}
		s=this.getTitle()+" "+this.name+" follows "+f+".";
//		"Monitor Stat Sensor A follows no channels."
//		"Monitor Stat Sensor A follows [Cafe Music BGM]."
//		"Monitor Stat Sensor A follows [Cafe Music BGM {#views: 1, max watch time: 20, avg watch time: 20.00}, I Love You Venice]."
		return s;
	}
	
	public void watchUpdated(Channel chan, int time) {
		for(int i=0; i<this.numChannel; i++) {
			if(this.Channels[i]==chan) {
				this.view[i]++;
				this.sum[i]+=time;
				if(time>this.max[i]) {
					this.max[i]=time;
				}
			}
		}
	}
}











