package model;

public class Follower {
	protected String name;
	protected int maxVideo;
	protected int maxChannel;
	protected int numVideo;
	protected int numChannel;
	protected Channel[] Channels;
	protected String[] videos;
	
//	public Follower(String name, int maxVideo, int maxChannel) {
//		super();
//		this.name = name;
//		this.maxVideo = maxVideo;
//		this.maxChannel = maxChannel;
//		this.numChannel=0;
//		this.numVideo=0;
//		this.videos=new String[maxVideo];
//		this.Channels=new Channel[maxChannel];
//	}
	
	public void follow(Channel ch) {
		if(this.numChannel<this.maxChannel) {
			this.Channels[this.numChannel]=ch;
			this.numChannel++;
		}
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public void unfollow(Channel channel) {
		int index=-1;
		for(int i=0; i<this.numChannel; i++) {
			if(this.Channels[i]==channel) {
				index=i;
			}
		}
		if(index<0) return;
		else {
			for(int j=index; j<this.numChannel-1; j++) {
				this.Channels[j]=this.Channels[j+1];
			}
			this.numChannel--;
		}
		
	}
}
