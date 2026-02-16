package model;

public class Channel {
	private String name;
	private int maxVideo;
	private int maxFollower;
	private int numVideo;
	private int numFollow;
	private String[] videos;
	private Follower[] follower;
	
	public Channel(String name, int maxVideo, int maxFollower) {
		super();
		this.name = name;
		this.maxVideo = maxVideo;
		this.maxFollower = maxFollower;
		this.numFollow=0;
		this.numVideo=0;
		this.videos=new String[maxVideo];
		this.follower=new Follower[maxFollower];
	}

	@Override
	public String toString() {
		String s="";
		String v ="";
		String f="";
		
		if(this.numFollow==0) f="has no followers";
		else {
			f+="is followed by [";
			for(int i=0; i<this.numFollow; i++) {
				f+=this.follower[i].getTitle()+" "+this.follower[i].getName();
				if(i<this.numFollow-1) {
					f+=", ";
				}
			}
			f+="]";
		}
		
		if(this.numVideo==0) v="no videos";
		else {
			v+="<";
			for(int i=0; i<this.numVideo; i++) {
				v+=this.videos[i];
				if(i<this.numVideo-1) {
					v+=", ";
				}
			}
			v+=">";
		}
		
		s=this.name+" released "+v+" and "+f+".";
//		"Cafe Music BGM released no videos and has no followers."
//		"Cafe Music BGM released <Monday Jazz> and has no followers."
		return s;
	}

	public void releaseANewVideo(String string) {
		if(this.numVideo<this.maxVideo) {
			this.videos[this.numVideo]=string;
			this.numVideo++;
			for(int i=0; i<this.numFollow; i++) {
				if(this.follower[i].getTitle()=="Subscriber") {
					((Subscriber) this.follower[i]).rec(string);
				}
			}
		}
	}

	public void follow(Follower fol) {
		if(this.numFollow<this.maxFollower) {
			this.follower[this.numFollow]=fol;
			this.numFollow++;
			fol.follow(this);
		}
		
	}

	public String getName() {
		return this.name;
	}

	public void unfollow(Follower f1) {
		int index=-1;
		for(int i=0; i<this.numFollow; i++) {
			if(this.follower[i]==f1) {
				index=i;
			}
		}
		if(index<0) return;
		else {
			for(int j=index; j<this.numFollow-1; j++) {
				this.follower[j]=this.follower[j+1];
			}
			f1.unfollow(this);
			this.numFollow--;
		}
		
	}

	public int getNumVideo() {
		return numVideo;
	}

	public String[] getVideos() {
		return videos;
	}

	public String[] setVideos(String[] videos) {
		String[] list = new String[this.numVideo];
		for(int i=0; i<this.numVideo; i++) {
			list[i]=this.videos[i];
		}
		return list;
	}

	public void watched(String video, int time) {
		for(int i=0; i<this.numVideo; i++) {
			if(this.videos[i].equals(video)) {
				for(int j=0; j<this.numFollow; j++) {
					if(this.follower[j].getTitle().equals("Monitor")) {
						((Monitor) follower[j]).watchUpdated(this, time);
					}
				}
			}
		}
		
	}
	
	
	
}
