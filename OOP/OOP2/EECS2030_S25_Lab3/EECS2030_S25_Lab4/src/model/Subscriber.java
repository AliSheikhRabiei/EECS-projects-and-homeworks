package model;

public class Subscriber extends Follower {

	public Subscriber(String name, int maxVideo, int maxChannel) {
		this.name = name;
		this.maxVideo = maxVideo;
		this.maxChannel = maxChannel;
		this.numChannel=0;
		this.numVideo=0;
		this.videos=new String[maxVideo];
		this.Channels=new Channel[maxChannel];
	}
	
	@Override
	public String getTitle() {
		return "Subscriber";
	}
	
	@Override
	public String toString() {
		String s="";
		String v ="";
		String f="";
		
		if(this.numChannel==0) f="no channels";
		else {
			f+="[";
			for(int j=0; j<this.numChannel; j++) {
				f+=this.Channels[j].getName();
				if(j<this.numChannel-1) {
					f+=", ";
				}
			}
			f+="]";
		}
		
		if(this.numVideo==0) v="has no recommended videos";
		else {
			v+="is recommended <";
			for(int i=0; i<this.numVideo; i++) {
				v+=this.videos[i];
				if(i<this.numVideo-1) {
					v+=", ";
				}
			}
			v+=">";
		}
		
		s=this.getTitle()+" "+this.name+" follows "+f+" and "+v+".";
//		"Subscriber Alan follows no channels and has no recommended videos."
//		"Cafe Music BGM released <Monday Jazz> and has no followers."
//		"Subscriber Alan follows [Cafe Music BGM] and has no recommended videos."
		return s;
	}
	
	public void rec(String vid) {
		if(this.numVideo<this.maxVideo) {
			this.videos[this.numVideo]=vid;
			this.numVideo++;
		}
	}

	public void watch(String video, int time) {
		for(int i=0; i<this.numChannel; i++ ) {
			this.Channels[i].watched(video, time);
			
		}
	}
	
	
	
}
